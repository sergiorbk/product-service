package com.sergosoft.productservice.web;

import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.mock.mockito.SpyBean;
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

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryCreateDto newCategory = CategoryCreateDto.builder()
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
        String title = "Electronics";
        CategoryEntity categoryToSave = CategoryEntity.builder()
                .title(title)
                .parent(null)
                .build();

        CategoryEntity savedCategory = categoryRepository.save(categoryToSave);

        mockMvc.perform(get("/api/v1/categories/{id}", savedCategory.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)));
    }

//    @Test
//    void shouldUpdateCategory() throws Exception {
//        CategoryEntity category = new CategoryEntity(null, "Sports", null);
//        CategoryEntity savedCategory = categoryRepository.save(category);
//
//        CategoryRequestDto updatedCategoryDto = CategoryRequestDto.builder()
//                .title("Outdoor Sports")
//                .parentId(null)
//                .build();
//
//        mockMvc.perform(put("/api/v1/categories/{id}", savedCategory.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedCategoryDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title", is("Outdoor Sports")));
//    }
//
//    @Test
//    void shouldDeleteCategory() throws Exception {
//        CategoryEntity category = new CategoryEntity(null, "Fashion", null);
//        CategoryEntity savedCategory = categoryRepository.save(category);
//
//        mockMvc.perform(delete("/api/v1/categories/{id}", savedCategory.getId()))
//                .andExpect(status().isNoContent());
//
//        Optional<CategoryEntity> deletedCategory = categoryRepository.findById(savedCategory.getId());
//        assert(deletedCategory.isEmpty());
//    }
}
