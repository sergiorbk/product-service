package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryCreationDto;

public interface CategoryService {

    Category getCategoryById(Integer id);
    Category createCategory(CategoryCreationDto dto);
    Category updateCategory(Integer id, CategoryCreationDto dto);
    void deleteCategoryById(Integer categoryId);
}
