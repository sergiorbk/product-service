package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductCreateDto;

import java.util.UUID;

public interface ProductService {

    ProductDetails getProductById(UUID id);
    ProductDetails createProduct(ProductCreateDto dto);
    ProductDetails updateProduct(UUID id, ProductCreateDto dto);
    void archiveProduct(UUID id);
    void deleteProductById(UUID id);
}
