package com.sergosoft.productservice.service.exception.category;

public class CategoryInUseException extends RuntimeException {

    private static final String CATEGORY_IN_USE_MESSAGE = "Category with id %s In Use.";

    public CategoryInUseException(String id) {
        super(String.format(CATEGORY_IN_USE_MESSAGE, id));
    }
}
