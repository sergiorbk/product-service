package com.sergosoft.productservice.service;

import com.sergosoft.productservice.config.MappersTestConfiguration;
import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryCreationDto;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.service.exception.category.CategoryNotFoundException;
import com.sergosoft.productservice.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CategoryServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Category Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CategoryServiceTests {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Captor
    private ArgumentCaptor<Category> categoryCaptor;

    @Test
    @DisplayName("Should retrieve category by ID")
    void shouldRetrieveCategoryById() {
        Category mockCategory = Category.builder().id(1).title("Electronics").build();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));

        Category result = categoryService.getCategoryById(1);

        assertEquals(mockCategory, result);
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should throw exception when category not found by ID")
    void shouldThrowExceptionWhenCategoryNotFoundById() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(99));
    }

    @Test
    @DisplayName("Should create new root category")
    void shouldCreateNewRootCategory() {
        CategoryCreationDto dto = CategoryCreationDto.builder().title("Root Category").build();
        Category categoryToSave = Category.builder().title("Root Category").parent(null).build();
        Category savedCategory = Category.builder().id(1).title("Root Category").build();

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        Category result = categoryService.createCategory(dto);

        assertEquals(savedCategory, result);
        verify(categoryRepository).save(categoryCaptor.capture());
        assertEquals("Root Category", categoryCaptor.getValue().getTitle());
    }

    @Test
    @DisplayName("Should create subcategory with parent")
    void shouldCreateSubcategoryWithParent() {
        Category parentCategory = Category.builder().id(1).title("Parent").build();
        CategoryCreationDto dto = CategoryCreationDto.builder().title("Subcategory").parentId(1).build();
        Category savedSubcategory = Category.builder().id(2).title("Subcategory").parent(parentCategory).build();

        when(categoryRepository.findById(1)).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(savedSubcategory);

        Category result = categoryService.createCategory(dto);

        assertEquals(savedSubcategory, result);
        verify(categoryRepository).save(categoryCaptor.capture());
        assertEquals(parentCategory, categoryCaptor.getValue().getParent());
    }

    @Test
    @DisplayName("Should update category title")
    void shouldUpdateCategoryTitle() {
        Category existingCategory = Category.builder().id(1).title("Old Title").build();
        CategoryCreationDto updateDto = CategoryCreationDto.builder().title("New Title").build();
        Category updatedCategory = Category.builder().id(1).title("New Title").build();

        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(1, updateDto);

        assertEquals(updatedCategory, result);
        verify(categoryRepository).save(categoryCaptor.capture());
        assertEquals("New Title", categoryCaptor.getValue().getTitle());
    }

    @Test
    @DisplayName("Should delete category by ID")
    void shouldDeleteCategoryById() {
        when(categoryRepository.existsById(1)).thenReturn(true);

        categoryService.deleteCategoryById(1);

        verify(categoryRepository, times(1)).deleteById(1);
    }
}
