package com.sergosoft.productservice.service.exception.category;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id %s not found";

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(UUID categoryId) {
        super(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
    }
}
