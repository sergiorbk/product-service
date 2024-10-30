package com.sergosoft.productservice.web;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.mapper.CategoryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryCreateDto creationDto) {
        Category categoryToCreate = CategoryMapper.INSTANCE.toEntity(creationDto);
        return ResponseEntity.ok(CategoryMapper.INSTANCE.toDto(categoryService.createCategory(categoryToCreate)));
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategoryById(categoryId);
        // todo logging
    }
}
