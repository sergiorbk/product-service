package com.sergosoft.productservice.service.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id %s not found";

    public ProductNotFoundException(UUID productId) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
    }
}
