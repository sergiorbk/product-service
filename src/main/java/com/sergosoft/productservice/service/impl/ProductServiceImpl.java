package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.domain.product.ProductStatus;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import com.sergosoft.productservice.elasticsearch.service.ProductSearchService;
import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.ProductService;
import com.sergosoft.productservice.service.exception.ProductNotFoundException;
import com.sergosoft.productservice.service.mapper.ProductMapper;
import com.sergosoft.productservice.util.SlugGenerator;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductSearchService productSearchService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductDetails getProductById(UUID id) {
        return productMapper.toProductDetails(retrieveProductByIdFromJpaOrElseThrow(id));
    }

    @Override
    @Transactional
    public ProductDetails createProduct(ProductCreateDto dto) {
        log.info("Creating product {}", dto);
        ProductEntity productToSave = productMapper.toProductEntity(dto);
        // add or change attributes via the builder
        productToSave = productToSave.toBuilder()
                .categories(new HashSet<>(categoryService.getCategoryEntitiesByIds(dto.getCategoryIds().stream().map(UUID::fromString).toList())))
                .build();
        // save created product to jpa and search repositories
        ProductEntity savedProduct = saveProductOrElseThrow(productToSave);
        productSearchService.createProductDocument(productMapper.toProductDocument(savedProduct));
        // return saved product details
        return productMapper.toProductDetails(savedProduct);
    }

    @Override
    @Transactional
    public ProductDetails updateProduct(UUID id, ProductCreateDto dto) {
        log.debug("Updating product with id {} with data: {}", id, dto);
        ProductEntity productToUpdate = retrieveProductByIdFromJpaOrElseThrow(id);
        productToUpdate = productToUpdate.toBuilder()
                .title(dto.getTitle() == null ? productToUpdate.getTitle() : dto.getTitle())
                .slug(SlugGenerator.generateSlug(dto.getTitle()))
                .description(dto.getDescription() == null ? productToUpdate.getDescription() : dto.getDescription())
                .price(dto.getPrice() == null ? productToUpdate.getPrice() : dto.getPrice())
                .categories(dto.getCategoryIds() == null ? productToUpdate.getCategories() :
                        new HashSet<>(categoryService.getCategoryEntitiesByIds(
                                dto.getCategoryIds().stream().map(UUID::fromString).toList())
                        )
                )
                .updatedAt(LocalDateTime.now())
                .build();
        // save updated product
        ProductEntity savedProduct = saveProductOrElseThrow(productToUpdate);
        productSearchService.createProductDocument(productMapper.toProductDocument(savedProduct));
        // return the product details
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
            productSearchService.createProductDocument(productMapper.toProductDocument(productToActivate));
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
                // delete archived product from elasticsearch
                productSearchService.deleteProductDocumentById(productToArchive.getId());
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
                // delete archived product from elasticsearch
                productSearchService.deleteProductDocumentById(productTopBan.getId());
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
           productSearchService.deleteProductDocumentById(id);
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
