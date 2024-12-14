package com.sergosoft.productservice.web;

import java.net.URI;
import java.util.List;

import com.sergosoft.productservice.domain.category.CategoryTree;
import com.sergosoft.productservice.dto.category.response.CategorySlimDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.dto.category.response.CategorySlimDtoList;
import com.sergosoft.productservice.dto.category.response.CategoryTreeDto;
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
    public ResponseEntity<CategorySlimDto> getCategoryBySlug(@PathVariable String slug) {
        CategoryDetails retrievedCategoryDetails = categoryService.getCategoryBySlug(slug);
        CategorySlimDto categorySlimDto = categoryMapper.toCategorySlimDto(retrievedCategoryDetails);
        return ResponseEntity.ok(categorySlimDto);
    }

    @GetMapping("/root")
    public ResponseEntity<CategorySlimDtoList> getRootCategories() {
        List<CategoryDetails> rootCategories = categoryService.getRootCategories();
        return ResponseEntity.ok(categoryMapper.toCategorySlimDtoList(rootCategories));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryTreeDto>> getAllCategories() {
        List<CategoryTree> categoryTree = categoryService.getAllCategoriesTree();
        return ResponseEntity.ok(categoryTree.stream().map(categoryMapper::toCategoryTreeDto).toList());
    }

    @GetMapping("/{parentSlug}/subcategories")
    public ResponseEntity<CategorySlimDtoList> getSubcategoriesByParentSlug(@PathVariable String parentSlug) {
        List<CategoryDetails> subcategories = categoryService.getSubCategoriesByParentSlug(parentSlug);
        return ResponseEntity.ok(categoryMapper.toCategorySlimDtoList(subcategories));
    }

    @PostMapping
    public ResponseEntity<CategorySlimDto> createCategory(@RequestBody @Valid CategoryCreateDto categoryDto) {
        CategoryDetails createdCategoryDetails = categoryService.createCategory(categoryDto);
        CategorySlimDto createdCategorySlimDto = categoryMapper.toCategorySlimDto(createdCategoryDetails);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategorySlimDto.getSlug())
                .toUri();
        return ResponseEntity.created(location).body(createdCategorySlimDto);
    }

    @PutMapping("/{categorySlug}")
    public ResponseEntity<CategorySlimDto> updateCategory(@PathVariable String categorySlug,
                                                          @RequestBody @Valid CategoryUpdateDto categoryDto) {
        CategoryDetails updatedCategoryDetails = categoryService.updateCategory(categorySlug, categoryDto);
        return ResponseEntity.ok(categoryMapper.toCategorySlimDto(updatedCategoryDetails));
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
