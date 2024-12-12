package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.repository.entity.CategoryEntity;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    CategoryDetails getCategoryBySlug(String slug);
    List<CategoryEntity> getCategoryEntitiesBySlugs(List<String> slugs);
    Set<CategoryDetails> getRootCategories();
    Set<CategoryDetails> getSubCategoriesByParentSlug(String parentSlug);
    CategoryDetails createCategory(CategoryCreateDto dto);
    CategoryDetails updateCategory(String slug, CategoryUpdateDto dto);
    void archiveCategoryBySlug(String slug);
    void deleteCategoryBySlug(String slug);
}
