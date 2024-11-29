package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ProductService {

    ProductDetails getProductById(UUID id);
    ProductDetails createProduct(ProductCreateDto dto);
    ProductDetails updateProduct(UUID id, ProductCreateDto dto);

    @Transactional
    void activateProduct(UUID id);

    void archiveProduct(UUID id);

    @Transactional
    void banProduct(UUID id);

    void deleteProductById(UUID id);
}
