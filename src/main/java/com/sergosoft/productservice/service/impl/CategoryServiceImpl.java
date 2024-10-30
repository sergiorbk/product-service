package com.sergosoft.productservice.service.impl;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.service.exception.CategoryNotFoundException;
import com.sergosoft.productservice.service.exception.ParentCategoryNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Integer id) {
        log.info("Getting product category by id: {}", id);
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category createCategory(CategoryCreateDto categoryCreateDto) {
        log.info("Creating new product category: {}", categoryCreateDto);
        Integer parentId = categoryCreateDto.getParentId();
        Category parentCategory = null;
        // if parent category id was specified
        if(parentId != null) {
            log.debug("Getting parent category with id: {}", categoryCreateDto.getParentId());
            parentCategory = categoryRepository.findById(categoryCreateDto.getParentId())
                    .orElseThrow(() -> new ParentCategoryNotFoundException(categoryCreateDto.getParentId()));
            log.debug("Retrieved parent category with id {}: {}", parentId, parentCategory);
        }
        Category categoryToSave = new Category(null, categoryCreateDto.getTitle(), parentCategory);
        log.debug("Saving new category: {}", categoryToSave);
        return categoryRepository.save(categoryToSave);
    }

    @Override
    public Category updateCategory(Integer id, CategoryCreateDto categoryDto) {
        log.info("Updating category with id: {}", id);
        log.debug("Retrieving category to update by id: {}", id);
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        log.debug("Retrieved category to update with id{}: {}", id, existingCategory);
        Integer dtoParentId = categoryDto.getParentId();

        Category updatedCategory = new Category(existingCategory.getId(), categoryDto.getTitle(),
                dtoParentId == null ? null : getCategoryById(dtoParentId));

        log.info("Saving updated category with id {}: {}", id, updatedCategory);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        log.info("Deleting category with id: {}", id);
        categoryRepository.delete(id);
    }
}
