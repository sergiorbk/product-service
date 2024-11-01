package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.dto.product.ProductCreationDto;

import java.util.UUID;

public interface ProductService {

    Product getProductById(UUID id);
    Product createProduct(ProductCreationDto dto);
    Product updateProduct(UUID id, ProductCreationDto dto);
    void deleteProductById(UUID id);
}
