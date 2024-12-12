package com.sergosoft.productservice.web;

import java.net.URI;
import java.util.Set;

import com.sergosoft.productservice.dto.category.CategorySetDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.service.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<CategoryResponseDto> getCategoryBySlug(@PathVariable String slug) {
        CategoryDetails retrievedCategoryDetails = categoryService.getCategoryBySlug(slug);
        CategoryResponseDto categoryResponseDto = categoryMapper.toCategoryResponseDto(retrievedCategoryDetails);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @GetMapping("/root")
    public ResponseEntity<CategorySetDto> getRootCategories() {
        Set<CategoryDetails> rootCategories = categoryService.getRootCategories();
        return ResponseEntity.ok(categoryMapper.toCategorySetDto(rootCategories));
    }

    @GetMapping("/{parentSlug}/subcategories")
    public ResponseEntity<CategorySetDto> getSubcategoriesByParentSlug(@PathVariable String parentSlug) {
        Set<CategoryDetails> subcategories = categoryService.getSubCategoriesByParentSlug(parentSlug);
        return ResponseEntity.ok(categoryMapper.toCategorySetDto(subcategories));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryCreateDto categoryDto) {
        CategoryDetails createdCategoryDetails = categoryService.createCategory(categoryDto);
        CategoryResponseDto createdCategoryResponseDto = categoryMapper.toCategoryResponseDto(createdCategoryDetails);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategoryResponseDto.getSlug())
                .toUri();
        return ResponseEntity.created(location).body(createdCategoryResponseDto);
    }

    @PutMapping("/{categorySlug}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable String categorySlug,
                                                              @RequestBody @Valid CategoryUpdateDto categoryDto) {
        CategoryDetails updatedCategoryDetails = categoryService.updateCategory(categorySlug, categoryDto);
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(updatedCategoryDetails));
    }

    @PutMapping("/{categorySlug}/archive")
    public ResponseEntity<Void> archiveCategory(@PathVariable String categorySlug) {
        categoryService.archiveCategoryBySlug(categorySlug);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categorySlug}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categorySlug) {
        categoryService.deleteCategoryBySlug(categorySlug);
        return ResponseEntity.noContent().build();
    }
}
