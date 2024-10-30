package com.sergosoft.productservice.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.mapper.CategoryMapper;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Integer id) {
        Category retrievedCategory = categoryService.getCategoryById(id);
        CategoryResponseDto categoryResponseDto = categoryMapper.toDto(retrievedCategory);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryCreateDto creationDto) {
        Category categoryToCreate = categoryMapper.toEntity(creationDto);
        Category createdCategory = categoryService.createCategory(categoryToCreate);
        CategoryResponseDto createdCategoryResponseDto = categoryMapper.toDto(createdCategory);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategoryResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdCategoryResponseDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
