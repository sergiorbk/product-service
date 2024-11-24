package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.dto.product.ProductCreationDto;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import com.sergosoft.productservice.service.exception.ProductNotFoundException;
import com.sergosoft.productservice.service.exception.category.CategoryNotFoundException;
import com.sergosoft.productservice.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.service.ProductService;
import com.sergosoft.productservice.repository.ProductRepository;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Product getProductById(Long id) {
        log.info("Getting product by id: {}", id);
        ProductEntity retrievedProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.info("Product was retrieved successfully: {}", retrievedProduct);
        return productMapper.toProduct(retrievedProduct);
    }

    @Override
    public Product createProduct(ProductCreationDto dto) {
        log.info("Creating new product: {}", dto);
        // todo implement getting ownerUUID from SecurityContextHolder
        ProductEntity productToSave = new ProductEntity(
                null,
                dto.getTitle(),
                dto.getDescription(),
                findCategoriesById(dto),
                dto.getPrice(),
                Instant.now()
        );
        ProductEntity savedProduct = productRepository.save(productToSave);
        log.info("New Product was created successfully: {}", savedProduct);
        return productMapper.toProduct(savedProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductCreationDto dto) {
        log.info("Updating product with id: {}", id);

        log.debug("Retrieving a product to update by id: {}", id);
        ProductEntity productToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.info("Retrieved product to update with id {}: {}", id, productToUpdate);

        ProductEntity updatedProduct = new ProductEntity(
                id,
                dto.getTitle(),
                dto.getDescription(),
                findCategoriesById(dto),
                dto.getPrice(),
                Instant.now()
        );

        log.info("Saving updated product with id {}: {}", id, updatedProduct);
        ProductEntity savedProduct = productRepository.save(updatedProduct);
        log.info("Updated Product was saved successfully: {}", savedProduct);
        return productMapper.toProduct(savedProduct);
    }

    // todo transfer this logic to repository layer
    private Set<CategoryEntity> findCategoriesById(ProductCreationDto dto) {
        Set<Optional<CategoryEntity>> optionalCategories = dto.getCategoriesIds().stream().map(categoryRepository::findById).collect(Collectors.toSet());
        Set<CategoryEntity> categories = new HashSet<>();
        for(Optional<CategoryEntity> optionalCategory : optionalCategories) {
            if(optionalCategory.isEmpty()) {
                throw new CategoryNotFoundException("No such product category found");
            }
            categories.add(optionalCategory.get());
        }
        return categories;
    }

    @Override
    public void deleteProductById(Long id) {
        log.info("Truing to delete product with id: {}", id);
        if(productRepository.existsById(id)) {
            log.info("Deleting existent product with id: {}", id);
            productRepository.deleteById(id);
            // check does product with such id still exist after deletion
            if(productRepository.existsById(id)) {
                log.info("Product with id {} was deleted successfully.", id);
            } else {
                log.error("Unable to Delete Product with id {}.", id);
            }
        } else {
            log.error("Unable to Delete non-existent Product with id {}.", id);
        }
    }
}
