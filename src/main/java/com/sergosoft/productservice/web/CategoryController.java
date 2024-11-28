package com.sergosoft.productservice.web;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable UUID id) {
        CategoryDetails retrievedCategoryDetails = categoryService.getCategoryById(id);
        CategoryResponseDto categoryResponseDto = categoryMapper.toCategoryResponseDto(retrievedCategoryDetails);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @GetMapping("/slug/{slug}")
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

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<CategorySetDto> getSubcategoriesByParentId(@PathVariable UUID parentId) {
        Set<CategoryDetails> subcategories = categoryService.getSubCategories(parentId);
        return ResponseEntity.ok(categoryMapper.toCategorySetDto(subcategories));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryCreateDto categoryDto) {
        CategoryDetails createdCategoryDetails = categoryService.createCategory(categoryDto);
        CategoryResponseDto createdCategoryResponseDto = categoryMapper.toCategoryResponseDto(createdCategoryDetails);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategoryResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdCategoryResponseDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID categoryId,
                                                              @RequestBody @Valid CategoryUpdateDto categoryDto) {
        CategoryDetails updatedCategoryDetails = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(updatedCategoryDetails));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
