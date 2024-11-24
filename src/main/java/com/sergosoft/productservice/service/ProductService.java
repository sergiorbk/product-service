package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.dto.product.ProductCreationDto;

public interface ProductService {

    Product getProductById(Long id);
    Product createProduct(ProductCreationDto dto);
    Product updateProduct(Long id, ProductCreationDto dto);
    void deleteProductById(Long id);
}
