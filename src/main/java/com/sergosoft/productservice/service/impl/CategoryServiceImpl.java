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
import com.sergosoft.productservice.service.exception.category.DuplicateCategorySlugException;
import com.sergosoft.productservice.service.mapper.CategoryMapper;
import com.sergosoft.productservice.util.SlugGenerator;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public CategoryDetails getCategoryBySlug(String slug) {
        log.debug("Retrieving category by slug: {}", slug);
        CategoryEntity retrievedCategory = retrieveCategoryBySlugOrElseThrow(slug);
        CategoryDetails categoryDetails = categoryMapper.toCategoryDetails(retrievedCategory);
        log.info("Retrieved category by slug {}: {}", slug, categoryDetails);
        return categoryDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryEntity> getCategoryEntitiesBySlugs(List<String> slugs) {
        log.debug("Retrieving categories by ids: {}", slugs);
        List<CategoryEntity> retrievedCategories;
        try {
            retrievedCategories = categoryRepository.findBySlugIn(slugs);
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving categories by slugs: {}", ex.getMessage());
            throw new PersistenceException(ex);
        }
        log.info("Retrieved categories list by ids: {}", slugs);
        return retrievedCategories;
    }

    @Override
    public List<CategoryDetails> getRootCategories() {
        log.debug("Retrieving root categories");
        List<CategoryEntity> rootCategories = categoryRepository.findByParentNull();
        List<CategoryDetails> rootCategoriesDetails = rootCategories.stream()
                .map(categoryMapper::toCategoryDetails).toList();
        log.info("Retrieved {} root categories.", rootCategories.size());
        return rootCategoriesDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDetails> getSubCategoriesByParentSlug(String parentSlug) {
        log.debug("Retrieving subcategories by parent category with slug: {}", parentSlug);
        CategoryEntity parentCategory = retrieveCategoryBySlugOrElseThrow(parentSlug);
        Set<CategoryEntity> subcategories = parentCategory.getSubcategories();
        List<CategoryDetails> subcategoriesDetails = subcategories.stream()
                .map(categoryMapper::toCategoryDetails).toList();
        log.info("Retrieved {} subcategories by parent category with slug {}", subcategoriesDetails.size(), parentSlug);
        return subcategoriesDetails;
    }

    @Override
    @Transactional
    public CategoryDetails createCategory(CategoryCreateDto dto) {
        log.debug("Creating category: {}", dto);
        // generate slug by title and check if the slug is unique
        String slug = SlugGenerator.generateSlug(dto.getTitle());
        if(categoryRepository.existsByNaturalId(slug)) {
            log.error("Category with title {} already exists", dto.getTitle());
            throw new DuplicateCategorySlugException(slug);
        }

        // retrieving parent category by slug if present
        CategoryEntity parentCategory = null;
        if(dto.getParentSlug() != null) {
            parentCategory = retrieveCategoryByIdOrElseThrow(UUID.fromString(dto.getParentSlug()));
        }

        // preparing category entity to save
        CategoryEntity categoryToSave = CategoryEntity.builder()
                .title(dto.getTitle())
                .slug(slug)
                .parent(parentCategory)
                .status(CategoryStatus.ACTIVE)
                .imageUrl(dto.getImageUrl())
                .build();

        // saving the category to DB
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
    public CategoryDetails updateCategory(String parentSlug, CategoryUpdateDto dto) {
        log.debug("Updating category: {}", dto);
        CategoryEntity categoryToUpdate = retrieveCategoryBySlugOrElseThrow(parentSlug);
        categoryToUpdate = categoryToUpdate.toBuilder()
                .title(dto.getTitle() == null ? categoryToUpdate.getTitle() : dto.getTitle())
                .parent(dto.getParentSlug() == null ? categoryToUpdate.getParent() :
                        retrieveCategoryBySlugOrElseThrow(dto.getParentSlug()))
                .status(dto.getStatus() == null ? categoryToUpdate.getStatus() : dto.getStatus())
                .imageUrl(dto.getImageUrl() == null ? categoryToUpdate.getImageUrl() : dto.getImageUrl())
                .build();

        // saving the updated category to DB
        CategoryEntity updatedCategory = saveCategoryOrElseThrow(categoryToUpdate);
        CategoryDetails categoryDetails = categoryMapper.toCategoryDetails(updatedCategory);
        log.info("Updated category: {}", categoryDetails);
        return categoryDetails;
    }

    @Override
    @Transactional
    public void archiveCategoryBySlug(String slug) {
        log.debug("Archiving category by slug: {}", slug);
        CategoryEntity categoryToArchive = retrieveCategoryBySlugOrElseThrow(slug);
        if(categoryToArchive.getStatus() == CategoryStatus.ARCHIVED) {
            log.error("Category with slug {} is already archived", slug);
            throw new IllegalArgumentException("Category with slug " + slug + " is already archived");
        }
        categoryToArchive.setStatus(CategoryStatus.ARCHIVED);
        CategoryEntity updatedCategory = saveCategoryOrElseThrow(categoryToArchive);
        log.info("Archived category with slug {}", updatedCategory.getId());
    }

    @Override
    @Transactional
    public void deleteCategoryBySlug(String slug) {
        log.debug("Deleting category: {}", slug);
        // check if category with such id exists
        CategoryEntity categoryToDelete = retrieveCategoryBySlugOrElseThrow(slug);
        // check if category has no subcategories
        if(!categoryToDelete.getSubcategories().isEmpty()) {
            log.error("Unable to delete category that has subcategories: {}", categoryToDelete.getSubcategories());
            throw new CategoryInUseException(slug);
        }
        // check if category has no related products
        if(!categoryToDelete.getRelatedProducts().isEmpty()) {
            log.error("Unable to delete category that has products: {}", categoryToDelete.getRelatedProducts());
            throw new CategoryInUseException(slug);
        }
        // try to delete the category
        try {
            categoryRepository.deleteByNaturalId(slug);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting category: {}", ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
        log.info("Deleted category: {}", slug);
    }

    @Transactional(readOnly = true)
    public CategoryEntity retrieveCategoryByIdOrElseThrow(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving category by id: {}", id);
            return new CategoryNotFoundException(id);
        });
    }

    @Transactional(readOnly = true)
    public CategoryEntity retrieveCategoryBySlugOrElseThrow(String slug) {
        return categoryRepository.findByNaturalId(slug).orElseThrow(() -> {
            log.error("Exception occurred while retrieving category by slug: {}", slug);
            return new CategoryNotFoundException(slug);
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
