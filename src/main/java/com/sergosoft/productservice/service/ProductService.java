package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import com.sergosoft.productservice.dto.product.ProductUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductDetails getProductById(UUID id);

    Page<ProductDetails> getProductsPageByOwnerReference(UUID ownerReference, Pageable pageable);

    ProductDetails createProduct(ProductCreateDto dto);

    ProductDetails updateProduct(UUID id, ProductUpdateDto dto);

    void activateProduct(UUID id);

    void archiveProduct(UUID id);

    void banProduct(UUID id);

    void deleteProductById(UUID id);

}
