package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.domain.product.ProductStatus;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
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
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductDetails getProductById(UUID id) {
        return productMapper.toProductDetails(retrieveProductByIdOrElseThrow(id));
    }

    @Override
    @Transactional
    public ProductDetails createProduct(ProductCreateDto dto) {
        log.info("Creating product {}", dto);
        ProductEntity productToSave = productMapper.toProductEntity(dto);
        productToSave = productToSave.toBuilder()
                .categories(new HashSet<>(categoryService.getCategoryEntitiesByIds(dto.getCategoryIds().stream().map(UUID::fromString).toList())))
                .build();
        ProductEntity savedProduct = saveProductOrElseThrow(productToSave);
        ProductDetails productDetails = productMapper.toProductDetails(savedProduct);
        log.info("Saved mapped product details {}", productDetails);
        return productDetails;
    }

    @Override
    @Transactional
    public ProductDetails updateProduct(UUID id, ProductCreateDto dto) {
        log.debug("Updating product with id {} with data: {}", id, dto);
        ProductEntity productToUpdate = retrieveProductByIdOrElseThrow(id);
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
        ProductEntity savedProduct = saveProductOrElseThrow(productToUpdate);
        ProductDetails savedProductDetails = productMapper.toProductDetails(savedProduct);
        log.info("Updated product details {}", savedProductDetails);
        return savedProductDetails;
    }

    /**
     * Changes product status to ARCHIVED and makes it invisible in search results
     */
    @Override
    @Transactional
    public void archiveProduct(UUID id) {
        log.debug("Archiving product with id: {}", id);
        ProductEntity productToArchive = retrieveProductByIdOrElseThrow(id);
        if(productToArchive.getStatus() != ProductStatus.ARCHIVED) {
            productToArchive.setStatus(ProductStatus.ARCHIVED);
            saveProductOrElseThrow(productToArchive);
            log.info("Product with id {} was archived", id);
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

    private ProductEntity retrieveProductByIdOrElseThrow(UUID id) {
        log.debug("Retrieving product by id: {}", id);
        ProductEntity retrievedProduct = productRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving product by id: {}", id);
            return new ProductNotFoundException(id.toString());
        });
        log.info("Retrieved product by id {}: {}", id, retrievedProduct);
        return retrievedProduct;
    }

    private ProductEntity saveProductOrElseThrow(ProductEntity productToSave) {
        log.debug("Saving product {}", productToSave);
        try {
            ProductEntity savedProduct = productRepository.save(productToSave);
            log.info("Saved product {}", savedProduct);
            return savedProduct;
        } catch (Exception ex) {
            log.error("Exception occurred while saving product {}: {}", productToSave, ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
    }
}
