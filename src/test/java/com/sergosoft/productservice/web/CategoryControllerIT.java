package com.sergosoft.productservice.web;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryUpdateDto;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.util.SlugGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Category Controller CRUD Tests")
class CategoryControllerCrudIT {

    private final CategoryCreateDto CATEGORY_CREATE_DTO = buildCreateCategoryDto();
    private CategoryDetails createdCategory;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        createdCategory = categoryService.createCategory(CATEGORY_CREATE_DTO);
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryCreateDto createCategoryDto = buildCreateCategoryDto();
        mockMvc.perform(post("/api/v1/categories")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createCategoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slug").exists())
                .andExpect(jsonPath("$.slug").value(SlugGenerator.generateSlug(createCategoryDto.getTitle())));
    }

    @Test
    void shouldGetCategoryById() throws Exception {
        mockMvc.perform(get("/api/v1/categories/{id}", createdCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").exists())
                .andExpect(jsonPath("$.slug").value(createdCategory.getSlug()));
    }

    @Test
    void shouldGetCategoryBySlug() throws Exception {
        mockMvc.perform(get("/api/v1/categories/slug/{slug}", createdCategory.getSlug()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").exists())
                .andExpect(jsonPath("$.slug").value(createdCategory.getSlug()));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        CategoryUpdateDto categoryToUpdateDto = CategoryUpdateDto.builder()
                .title("Updated Title")
                .build();

        mockMvc.perform(put("/api/v1/categories/{categoryId}", createdCategory.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(categoryToUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value(categoryToUpdateDto.getTitle()));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/v1/categories/{categoryId}", createdCategory.getId()))
                .andExpect(status().isNoContent());
    }

    CategoryCreateDto buildCreateCategoryDto() {
        return CategoryCreateDto.builder()
                .title(RandomStringUtils.randomAlphabetic(10))
                .parentId(null)
                .build();
    }
}
