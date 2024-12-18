package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.repository.entity.CategoryEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    CategoryDetails getCategoryById(UUID id);
    CategoryDetails getCategoryBySlug(String slug);
    List<CategoryDetails> getCategoriesByIds(List<UUID> ids);
    List<CategoryEntity> getCategoryEntitiesByIds(List<UUID> ids);
    Set<CategoryDetails> getRootCategories();
    Set<CategoryDetails> getSubCategories(UUID parentId);
    CategoryDetails createCategory(CategoryCreateDto dto);
    CategoryDetails updateCategory(UUID id, CategoryUpdateDto dto);
    void archiveCategoryById(UUID id);
    void deleteCategoryById(UUID categoryId);
}
