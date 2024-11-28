package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductCreateDto;

public interface ProductService {

    ProductDetails getProductById(String id);
    ProductDetails createProduct(ProductCreateDto dto);
    ProductDetails updateProduct(String id, ProductCreateDto dto);
    void archiveProduct(String id);
    void deleteProductById(String id);
}
