package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryRequestDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;

import java.util.Set;

public interface CategoryService {

    CategoryDetails getCategoryById(Long id);
    Set<CategoryDetails> getRootCategories();
    Set<CategoryDetails> getSubCategories(Long parentId);
    CategoryDetails createCategory(CategoryRequestDto dto);
    CategoryDetails updateCategory(Long id, CategoryUpdateDto dto);
    void deleteCategoryById(Long categoryId);
}
