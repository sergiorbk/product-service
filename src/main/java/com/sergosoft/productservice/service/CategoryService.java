package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.category.CategoryTree;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;

import java.util.List;

public interface CategoryService {

    CategoryDetails getCategoryBySlug(String slug);
    List<CategoryTree> getAllCategoriesTree();
    List<CategoryDetails> getRootCategories();
    List<CategoryDetails> getSubCategoriesByParentSlug(String parentSlug);
    CategoryDetails createCategory(CategoryCreateDto dto);
    CategoryDetails updateCategory(String slug, CategoryUpdateDto dto);
    void archiveCategoryBySlug(String slug);
    void deleteCategoryBySlug(String slug);
}
