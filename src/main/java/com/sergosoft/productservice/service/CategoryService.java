package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Category;

public interface CategoryService {

    Category getCategoryById(Integer id);
    Category createCategory(Category category);
    void deleteCategoryById(Integer categoryId);
}
