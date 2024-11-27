package com.sergosoft.productservice.web;

import java.util.Optional;

import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.dto.category.CategoryRequestDto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Category Controller IT")
@Tag("category-service")
class CategoryDetailsControllerIT extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryRequestDto newCategory = CategoryRequestDto.builder()
                .title("Electronics")
                .parentId(null)
                .build();

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Electronics")));
    }

    @Test
    void shouldGetCategoryById() throws Exception {
        CategoryEntity category = new CategoryEntity(null, "Books", null);
        CategoryEntity savedCategory = categoryRepository.save(category);

        mockMvc.perform(get("/api/v1/categories/{id}", savedCategory.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Books")));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        CategoryEntity category = new CategoryEntity(null, "Sports", null);
        CategoryEntity savedCategory = categoryRepository.save(category);

        CategoryRequestDto updatedCategoryDto = CategoryRequestDto.builder()
                .title("Outdoor Sports")
                .parentId(null)
                .build();

        mockMvc.perform(put("/api/v1/categories/{id}", savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Outdoor Sports")));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        CategoryEntity category = new CategoryEntity(null, "Fashion", null);
        CategoryEntity savedCategory = categoryRepository.save(category);

        mockMvc.perform(delete("/api/v1/categories/{id}", savedCategory.getId()))
                .andExpect(status().isNoContent());

        Optional<CategoryEntity> deletedCategory = categoryRepository.findById(savedCategory.getId());
        assert(deletedCategory.isEmpty());
    }
}
