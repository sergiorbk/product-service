package com.sergosoft.productservice.service.exception.category;

public class CategoryNotFoundException extends RuntimeException {

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with id %d not found.";

    public CategoryNotFoundException(Integer categoryId) {
        super(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
    }
}
