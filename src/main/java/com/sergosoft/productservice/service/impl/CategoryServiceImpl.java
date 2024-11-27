package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryRequestDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.exception.CategoryNotFoundException;
import com.sergosoft.productservice.service.mapper.CategoryMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public CategoryDetails getCategoryById(Long id) {
        log.debug("Retrieving category by id: {}", id);
        CategoryEntity retrievedCategory = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving category by id: {}", id);
            throw new CategoryNotFoundException(id);
        });
        CategoryDetails categoryDetails = categoryMapper.toCategoryDetails(retrievedCategory);
        log.info("Retrieved category: {} : {}", categoryDetails, categoryDetails);
        return categoryDetails;
    }

    @Override
    @Transactional
    public CategoryDetails createCategory(CategoryRequestDto dto) {
        log.debug("Creating category: {}", dto);
        // retrieving parent category by id
        CategoryEntity parentCategory = null;
        if(dto.getParentId() != null) {
            parentCategory = categoryRepository.findById(dto.getParentId()).orElseThrow(() -> {
                log.error("Exception occurred while retrieving parent category by id: {}", dto.getParentId());
                throw new CategoryNotFoundException(dto.getParentId());
            });
        }
        CategoryEntity categoryToSave = CategoryEntity.builder()
                .title(dto.getTitle())
                .parent(parentCategory)
                .build();
        try {
            categoryRepository.save(categoryToSave);
        } catch (Exception ex) {
            log.error("Exception occurred while creating category: {}", ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
        CategoryDetails categoryDetails = categoryMapper.toCategoryDetails(categoryToSave);
        log.info("Created category: {}", categoryDetails);
        return categoryDetails;
    }

    @Override
    @Transactional
    public CategoryDetails updateCategory(Long id, CategoryUpdateDto dto) {
        log.debug("Updating category: {}", dto);
        CategoryEntity categoryToUpdate = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving category by id: {}", id);
            throw new CategoryNotFoundException(id);
        });
        categoryToUpdate.toBuilder()
                .title(dto.getTitle() == null ? null : dto.getTitle())
                .parent(dto.getParentId() == null ? null : categoryRepository.findById(dto.getParentId()).orElseThrow(() -> {
                    log.error("Exception occurred while retrieving parent category by id: {}", id);
                    return new CategoryNotFoundException(id);
                }));

        return null;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        log.debug("Deleting category: {}", categoryId);
    }
}
