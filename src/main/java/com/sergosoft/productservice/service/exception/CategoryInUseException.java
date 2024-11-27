package com.sergosoft.productservice.service.exception;

public class CategoryInUseException extends RuntimeException {

    private static final String CATEGORY_IN_USE_MESSAGE = "Category with id %s In Use.";

    public CategoryInUseException(Long id) {
        super(String.format(CATEGORY_IN_USE_MESSAGE, id));
    }
}
