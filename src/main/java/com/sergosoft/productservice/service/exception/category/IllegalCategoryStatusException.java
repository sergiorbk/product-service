package com.sergosoft.productservice.service.exception.category;

public class IllegalCategoryStatusException extends RuntimeException {

    public IllegalCategoryStatusException(String message) {
        super(message);
    }
}
