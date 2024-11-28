package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;

import java.util.Set;

public interface CategoryService {

    CategoryDetails getCategoryById(Long id);
    CategoryDetails getCategoryBySlug(String slug);
    Set<CategoryDetails> getRootCategories();
    Set<CategoryDetails> getSubCategories(Long parentId);
    CategoryDetails createCategory(CategoryCreateDto dto);
    CategoryDetails updateCategory(Long id, CategoryUpdateDto dto);
    void archiveCategoryById(Long id);
    void deleteCategoryById(Long categoryId);
}
