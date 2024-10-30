package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Category;

public interface CategoryService {

    Category createCategory(Category category);
    void deleteCategoryById(Integer categoryId);
}
