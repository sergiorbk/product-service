package com.sergosoft.productservice.service.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id %d not found";

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long categoryId) {
        super(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
    }
}
