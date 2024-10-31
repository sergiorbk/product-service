package com.sergosoft.productservice.service.exception.category;

public class ParentCategoryNotFoundException extends RuntimeException {

    private static final String PARENT_CATEGORY_NOT_FOUND_MESSAGE = "Parent category with id %d not found";

    public ParentCategoryNotFoundException(Integer parentId) {
        super(String.format(PARENT_CATEGORY_NOT_FOUND_MESSAGE, parentId));
    }
}
