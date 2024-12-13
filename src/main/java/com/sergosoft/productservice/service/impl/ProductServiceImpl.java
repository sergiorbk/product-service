package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.domain.product.ProductStatus;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import com.sergosoft.productservice.dto.product.ProductUpdateDto;
import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import com.sergosoft.productservice.service.ProductService;
import com.sergosoft.productservice.service.exception.ProductNotFoundException;
import com.sergosoft.productservice.service.mapper.ProductMapper;
import com.sergosoft.productservice.util.SlugGenerator;

import jakarta.persistence.PersistenceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductDetails getProductById(UUID id) {
        return productMapper.toProductDetails(retrieveProductByIdFromJpaOrElseThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetails> getProductsPageByOwnerReference(UUID ownerReference, Pageable pageable) {
        log.debug("Retrieving products page: {} by ownerReference: {}", pageable.getPageNumber(), ownerReference);
        Page<ProductEntity> productEntityPage;
        try {
            productEntityPage = productRepository.findAllByOwnerReference(pageable, ownerReference);
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving a product page: {}", ex.getMessage());
            throw new ProductNotFoundException(ex.getMessage());
        }
        log.info("Retrieved products page number: {} by userReference: {}", pageable.getPageNumber(), ownerReference);
        return productEntityPage.map(productMapper::toProductDetails);
    }

    @Override
    @Transactional
    public ProductDetails createProduct(ProductCreateDto dto) {
        log.info("Creating product {}", dto);
        ProductEntity productToSave = ProductEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .ownerReference(UUID.fromString(dto.getOwnerReference()))
                .price(dto.getPrice())
                .categories(dto.getCategoryIds()
                        .stream().map(slug -> CategoryEntity.builder().slug(slug).build()).toList())
                .build();

        // save created product to jpa and search repositories
        ProductEntity savedProduct = saveProductOrElseThrow(productToSave);
        // return saved product details
        return productMapper.toProductDetails(savedProduct);
    }

    @Override
    @Transactional
    public ProductDetails updateProduct(UUID id, ProductUpdateDto dto) {
        log.debug("Updating product with id {} with data: {}", id, dto);
        ProductEntity productToUpdate = retrieveProductByIdFromJpaOrElseThrow(id);
        productToUpdate = productToUpdate.toBuilder()
                .title(dto.getTitle() == null ? productToUpdate.getTitle() : dto.getTitle())
                .slug(SlugGenerator.generateSlug(dto.getTitle()))
                .description(dto.getDescription() == null ? productToUpdate.getDescription() : dto.getDescription())
                .price(dto.getPrice() == null ? productToUpdate.getPrice() : dto.getPrice())
                .categories(dto.getCategoryIds() == null ? productToUpdate.getCategories() : dto.getCategoryIds()
                        .stream().map(slug -> CategoryEntity.builder().slug(slug).build()).toList())
                .build();
        // save updated product
        ProductEntity savedProduct = saveProductOrElseThrow(productToUpdate);
        ProductDetails savedProductDetails = productMapper.toProductDetails(savedProduct);
        log.info("Updated product details {}", savedProductDetails);
        return savedProductDetails;
    }

    @Override
    @Transactional
    public void activateProduct(UUID id) {
        log.debug("Activating product with id {}", id);
        ProductEntity productToActivate = retrieveProductByIdFromJpaOrElseThrow(id);
        if(productToActivate.getStatus() != ProductStatus.ACTIVE) {
            productToActivate.setStatus(ProductStatus.ACTIVE);
            saveProductOrElseThrow(productToActivate);
            log.info("Product with id {} was activated", id);
            return;
        }
        log.warn("Product with id {} was already activated", id);
    }

    /**
     * Changes product status to ARCHIVED and makes it invisible in search results
     */
    @Override
    @Transactional
    public void archiveProduct(UUID id) {
        log.debug("Archiving product with id: {}", id);
        ProductEntity productToArchive = retrieveProductByIdFromJpaOrElseThrow(id);
        if(productToArchive.getStatus() != ProductStatus.ARCHIVED) {
            productToArchive.setStatus(ProductStatus.ARCHIVED);
            try {
                productRepository.save(productToArchive);
            } catch (Exception e) {
                log.error("Exception occurred wile archiving product: {}", e.getMessage());
            }
            log.info("Product with id {} was archived", id);
            return;
        }
        log.warn("Product with id {} was already archived", id);
    }

    @Override
    @Transactional
    public void banProduct(UUID id) {
        log.debug("Banning product with id: {}", id);
        ProductEntity productTopBan = retrieveProductByIdFromJpaOrElseThrow(id);
        if(productTopBan.getStatus() != ProductStatus.BANNED) {
            productTopBan.setStatus(ProductStatus.BANNED);
            try {
                productRepository.save(productTopBan);
            } catch (Exception e) {
                log.error("Exception occurred wile banning product: {}", e.getMessage());
            }
            log.info("Product with id {} was banned", id);
        }
    }

    /**
     * Delete ProductEntity from DB<p>
     * Use only for unacceptable products before publication
     */
    @Override
    @Transactional
    public void deleteProductById(UUID id) {
        log.info("Delete product by id: {}", id);
        try {
            productRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while hard deleting product with id {}: {}", id, ex.getMessage());
        }
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ProductEntity retrieveProductByIdFromJpaOrElseThrow(UUID id) {
        log.debug("Retrieving product by id: {}", id);
        ProductEntity retrievedProduct = productRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving product by id: {}", id);
            return new ProductNotFoundException(id.toString());
        });
        log.info("Retrieved product by id {}: {}", id, retrievedProduct);
        return retrievedProduct;
    }

    @Transactional
    public ProductEntity saveProductOrElseThrow(ProductEntity productToSave) {
        log.debug("Saving product to JPA repository {}", productToSave);
        try {
            ProductEntity savedProduct = productRepository.save(productToSave);
            log.info("Saved product to JPA repository {}", savedProduct);
            return savedProduct;
        } catch (Exception ex) {
            log.error("Exception occurred while saving product to JPA repository {}: {}", productToSave, ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
    }

}
