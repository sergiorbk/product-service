package com.sergosoft.productservice.service.exception.category;

public class DuplicateCategorySlugException extends RuntimeException {

    private static final String UNIQUE_SLUG_VIOLATION_MESSAGE = "Category with slug %s already exists.";

    public DuplicateCategorySlugException(String slug) {
        super(String.format(UNIQUE_SLUG_VIOLATION_MESSAGE, slug));
    }
}
