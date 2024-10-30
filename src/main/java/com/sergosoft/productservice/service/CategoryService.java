package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;

public interface CategoryService {

    Category getCategoryById(Integer id);
    Category createCategory(CategoryCreateDto dto);
    Category updateCategory(Integer id, CategoryCreateDto dto);
    void deleteCategoryById(Integer categoryId);
}
