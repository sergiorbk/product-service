package com.sergosoft.productservice.web;

import java.net.URI;

import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.service.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import com.sergosoft.productservice.domain.CategoryDetails;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.dto.category.CategoryRequestDto;
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
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        CategoryDetails retrievedCategoryDetails = categoryService.getCategoryById(id);
        CategoryResponseDto categoryResponseDto = categoryMapper.toCategoryDetailsDto(retrievedCategoryDetails);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryRequestDto categoryDto) {
        CategoryDetails createdCategoryDetails = categoryService.createCategory(categoryDto);
        CategoryResponseDto createdCategoryResponseDto = categoryMapper.toCategoryDetailsDto(createdCategoryDetails);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategoryResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdCategoryResponseDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId,
                                                              @RequestBody @Valid CategoryUpdateDto categoryDto) {
        CategoryDetails updatedCategoryDetails = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(categoryMapper.toCategoryDetailsDto(updatedCategoryDetails));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
