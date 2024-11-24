package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryCreationDto;

public interface CategoryService {

    Category getCategoryById(Long id);
    Category createCategory(CategoryCreationDto dto);
    Category updateCategory(Long id, CategoryCreationDto dto);
    void deleteCategoryById(Long categoryId);
}
