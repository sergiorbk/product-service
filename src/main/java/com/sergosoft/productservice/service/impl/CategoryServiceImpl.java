package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.category.CategoryStatus;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.exception.category.CategoryInUseException;
import com.sergosoft.productservice.service.exception.category.CategoryNotFoundException;
import com.sergosoft.productservice.service.mapper.CategoryMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public CategoryDetails getCategoryById(UUID id) {
        log.debug("Retrieving category by id: {}", id);
        CategoryEntity retrievedCategory = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving category by id: {}", id);
            return new CategoryNotFoundException(id);
        });
        CategoryDetails categoryDetails = categoryMapper.toCategoryDetails(retrievedCategory);
        log.info("Retrieved category: {} : {}", categoryDetails, categoryDetails);
        return categoryDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDetails> getCategoriesByIds(List<UUID> ids) {
        List<CategoryEntity> retrievedCategories = getCategoryEntitiesByIds(ids);
        List<CategoryDetails> retrievedCategoriesDetails = retrievedCategories.stream().map(categoryMapper::toCategoryDetails).toList();
        log.info("Retrieved mapped category details list: {}", retrievedCategoriesDetails);
        return retrievedCategoriesDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryEntity> getCategoryEntitiesByIds(List<UUID> ids) {
        log.debug("Retrieving categories by ids: {}", ids);
        List<CategoryEntity> retrievedCategories = new ArrayList<>();
        for(UUID id : ids) {
            retrievedCategories.add(retrieveCategoryByIdOrElseThrow(id));
            log.debug("Retrieved category with id {} was added to categories list.", id);
        }
        log.info("Retrieved categories list: {}", retrievedCategories);
        return retrievedCategories;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDetails getCategoryBySlug(String slug) {
        log.debug("Retrieving category by slug: {}", slug);
        CategoryEntity retrievedCategory = categoryRepository.findBySlug(slug).orElseThrow(() -> {
            log.error("Exception occurred while retrieving category by slug: {}", slug);
            return new CategoryNotFoundException(slug);
        });
        CategoryDetails categoryDetails = categoryMapper.toCategoryDetails(retrievedCategory);
        log.info("Retrieved category by slug {}: {}", slug, categoryDetails);
        return categoryDetails;
    }

    @Override
    public Set<CategoryDetails> getRootCategories() {
        log.debug("Retrieving root categories");
        Set<CategoryEntity> rootCategories = categoryRepository.findByParentNull();
        Set<CategoryDetails> rootCategoriesDetails = rootCategories.stream()
                .map(categoryMapper::toCategoryDetails).collect(Collectors.toSet());
        log.info("Retrieved {} root categories.", rootCategories.size());
        return rootCategoriesDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CategoryDetails> getSubCategories(UUID parentId) {
        log.debug("Retrieving subcategories by parent category parentId: {}", parentId);
        CategoryEntity parentCategory = retrieveCategoryByIdOrElseThrow(parentId);
        Set<CategoryEntity> subcategories = parentCategory.getSubcategories();
        Set<CategoryDetails> subcategoriesDetails = subcategories.stream()
                .map(categoryMapper::toCategoryDetails).collect(Collectors.toSet());
        log.info("Retrieved {} subcategories by parent category with parentId {}", subcategoriesDetails.size(), parentId);
        return subcategoriesDetails;
    }

    @Override
    @Transactional
    public CategoryDetails createCategory(CategoryCreateDto dto) {
        log.debug("Creating category: {}", dto);
        // retrieving parent category by id
        CategoryEntity parentCategory = null;
        if(dto.getParentId() != null) {
            // getting parent category entity
            parentCategory = retrieveCategoryByIdOrElseThrow(UUID.fromString(dto.getParentId()));
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
    public CategoryDetails updateCategory(UUID id, CategoryUpdateDto dto) {
        log.debug("Updating category: {}", dto);
        CategoryEntity categoryToUpdate = retrieveCategoryByIdOrElseThrow(id);
        categoryToUpdate.toBuilder()
                .title(dto.getTitle() == null ? categoryToUpdate.getTitle() : dto.getTitle())
                .parent(dto.getParentId() == null ? categoryToUpdate.getParent() : categoryRepository.findById(UUID.fromString(dto.getParentId()))
                        .orElseThrow(() -> {
                            log.error("Exception occurred while retrieving parent category by id: {}", id);
                            return new CategoryNotFoundException(id);
                }))
                .status(dto.getStatus() == null ? categoryToUpdate.getStatus() : CategoryStatus.valueOf(dto.getStatus()));
        CategoryEntity updatedCategory = categoryRepository.save(categoryToUpdate);
        CategoryDetails categoryDetails = categoryMapper.toCategoryDetails(updatedCategory);
        log.info("Updated category: {}", categoryDetails);
        return categoryDetails;
    }

    @Override
    @Transactional
    public void archiveCategoryById(UUID id) {
        log.debug("Archiving category by id: {}", id);
        CategoryEntity categoryToArchive = retrieveCategoryByIdOrElseThrow(id);
        if(categoryToArchive.getStatus() == CategoryStatus.ARCHIVED) {
            log.error("Category with id {} is already archived", id);
            throw new IllegalArgumentException("Category with id " + id + " is already archived");
        }
        categoryToArchive.setStatus(CategoryStatus.ARCHIVED);
        CategoryEntity updatedCategory = saveCategoryOrElseThrow(categoryToArchive);
        log.info("Archived category with id {}", updatedCategory.getId());
    }

    @Override
    @Transactional
    public void deleteCategoryById(UUID categoryId) {
        log.debug("Deleting category: {}", categoryId);
        // check if category with such id exists
        CategoryEntity categoryToDelete = retrieveCategoryByIdOrElseThrow(categoryId);
        // check if category has no subcategories
        if(!categoryToDelete.getSubcategories().isEmpty()) {
            log.error("Unable to delete category that has subcategories: {}", categoryToDelete.getSubcategories());
            throw  new CategoryInUseException(categoryId);
        }
        // check if category has no related products
        if(!categoryToDelete.getRelatedProducts().isEmpty()) {
            log.error("Unable to delete category that has products: {}", categoryToDelete.getRelatedProducts());
            throw new CategoryInUseException(categoryId);
        }
        // try to delete the category
        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting category: {}", ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
        log.info("Deleted category: {}", categoryId);
    }

    @Transactional(readOnly = true)
    public CategoryEntity retrieveCategoryByIdOrElseThrow(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving category by id: {}", id);
            return new CategoryNotFoundException(id);
        });
    }

    @Transactional(readOnly = true)
    public CategoryEntity saveCategoryOrElseThrow(CategoryEntity category) {
        log.debug("Saving category: {}", category);
        try {
            CategoryEntity savedCategory = categoryRepository.save(category);
            log.info("Saved category: {}", savedCategory);
            return savedCategory;
        } catch (Exception ex) {
            log.error("Exception occurred while saving category: {}", ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
    }
}
