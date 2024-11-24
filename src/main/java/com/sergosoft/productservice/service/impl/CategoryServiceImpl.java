package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.repository.entity.CategoryEntity;
import com.sergosoft.productservice.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.dto.category.CategoryCreationDto;
import com.sergosoft.productservice.service.exception.category.CategoryNotFoundException;
import com.sergosoft.productservice.service.exception.category.ParentCategoryNotFoundException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category getCategoryById(Long id) {
        log.info("Getting product category by id: {}", id);
        CategoryEntity retrievedCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        log.info("Category was retrieved successfully: {}", retrievedCategory);
        return categoryMapper.toCategory(retrievedCategory);
    }

    @Override
    public Category createCategory(CategoryCreationDto categoryCreationDto) {
        log.info("Creating new product category: {}", categoryCreationDto);
        Long parentId = categoryCreationDto.getParentId();
        CategoryEntity parentCategory = null;
        // if parent category id was specified
        if(parentId != null) {
            log.debug("Getting parent category with id: {}", categoryCreationDto.getParentId());
            parentCategory = categoryRepository.findById(categoryCreationDto.getParentId())
                    .orElseThrow(() -> new ParentCategoryNotFoundException(categoryCreationDto.getParentId()));
            log.debug("Retrieved parent category with id {}: {}", parentId, parentCategory);
        }
        CategoryEntity categoryToSave = new CategoryEntity(null, categoryCreationDto.getTitle(), parentCategory);
        log.debug("Saving new category: {}", categoryToSave);

        CategoryEntity savedCategory = categoryRepository.save(categoryToSave);
        log.info("New category was saved successfully: {}", savedCategory);
        return categoryMapper.toCategory(savedCategory);
    }

    @Override
    public Category updateCategory(Long id, CategoryCreationDto categoryDto) {
        log.info("Updating category with id: {}", id);
        log.debug("Retrieving category to update by id: {}", id);
        CategoryEntity existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        log.debug("Retrieved category to update with id{}: {}", id, existingCategory);
        Optional<CategoryEntity> parent = categoryRepository.findById(categoryDto.getParentId());

        CategoryEntity updatedCategory = new CategoryEntity(
                existingCategory.getId(),
                categoryDto.getTitle(),
                parent.orElse(null)
        );

        log.info("Saving updated category with id {}: {}", id, updatedCategory);

        CategoryEntity savedCategory = categoryRepository.save(updatedCategory);
        log.info("Updated category was saved successfully: {}", savedCategory);
        return categoryMapper.toCategory(savedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        log.info("Truing to delete a category with id: {}", id);
        if(categoryRepository.existsById(id)) {
            log.info("Deleting existent category with id: {}", id);
            categoryRepository.deleteById(id);
            // check does category with such id still exist after deletion
            if(categoryRepository.existsById(id)) {
                log.info("Category with id {} was deleted successfully.", id);
            } else {
                log.error("Unable to Delete Category with id {}.", id);
            }
        } else {
            log.error("Unable to Delete non-existent Category with id {}.", id);
        }
    }
}
