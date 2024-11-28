package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;

import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    CategoryDetails getCategoryById(UUID id);
    CategoryDetails getCategoryBySlug(String slug);
    Set<CategoryDetails> getRootCategories();
    Set<CategoryDetails> getSubCategories(UUID parentId);
    CategoryDetails createCategory(CategoryCreateDto dto);
    CategoryDetails updateCategory(UUID id, CategoryUpdateDto dto);
    void archiveCategoryById(UUID id);
    void deleteCategoryById(UUID categoryId);
}
