package com.sergosoft.productservice.service.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id %s not found";

    public ProductNotFoundException(String productId) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
    }
}
