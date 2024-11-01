package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.dto.product.ProductCreationDto;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.service.ProductService;
import com.sergosoft.productservice.repository.ProductRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    @Override
    public Product getProductById(UUID id) {
        log.info("Getting product by id: {}", id);
        Product retrievedProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.info("Product was retrieved successfully: {}", retrievedProduct);
        return retrievedProduct;
    }

    @Override
    public Product createProduct(ProductCreationDto dto) {
        log.info("Creating new product: {}", dto);
        List<Category> categories = dto.getCategoriesIds().stream().map(categoryService::getCategoryById).toList();
        // todo implement getting ownerUUID from SecurityContextHolder
        Product productToSave = new Product(null, null, dto.getTitle(), dto.getDescription(), categories,
                dto.getPrice(), Instant.now());
        Product savedProduct = productRepository.save(productToSave);
        log.info("New Product was created successfully: {}", savedProduct);
        return savedProduct;
    }

    @Override
    public Product updateProduct(UUID id, ProductCreationDto dto) {
        log.info("Updating product with id: {}", id);

        log.debug("Retrieving a product to update by id: {}", id);
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.info("Retrieved product to update with id {}: {}", id, productToUpdate);

        List<Category> categories = dto.getCategoriesIds().stream().map(categoryService::getCategoryById).toList();
        Product updatedProduct = new Product(id, productToUpdate.getOwnerId(), dto.getTitle(), dto.getDescription(),
                categories, dto.getPrice(), Instant.now());

        log.info("Saving updated product with id {}: {}", id, updatedProduct);
        Product savedProduct = productRepository.save(updatedProduct);
        log.info("Updated Product was saved successfully: {}", savedProduct);
        return savedProduct;
    }

    @Override
    public void deleteProductById(UUID id) {
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
