package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.dto.product.ProductCreateDto;

import java.util.UUID;

public interface ProductService {

    Product getProductById(UUID id);
    Product createProduct(ProductCreateDto dto);
    Product updateProduct(UUID id, ProductCreateDto dto);
    void deleteProductById(UUID id);
}
