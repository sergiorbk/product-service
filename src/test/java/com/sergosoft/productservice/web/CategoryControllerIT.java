package com.sergosoft.productservice.web;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;
import com.sergosoft.productservice.service.CategoryService;

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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.mockito.Mockito.reset;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Category Controller IT")
class CategoryControllerIT extends IntegrationTest {

    private final CategoryCreateDto CATEGORY_CREATE_DTO = buildCreateCategoryDto();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        reset(categoryService);
    }

    @Test
    void shouldCreateCategory() throws Exception {
        // Mocking external service using WireMock
        stubFor(WireMock.post("/api/v1/categories")
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(CategoryResponseDto.builder()
                                .title("Computers")
                                .slug("computers")
                                .parentId(null)
                                .build()))));

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CATEGORY_CREATE_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slug").exists())
                .andExpect(jsonPath("$.slug").value("computers"));
    }

    CategoryCreateDto buildCreateCategoryDto() {
        return CategoryCreateDto.builder()
                .title("Computers")
                .parentId(null)
                .build();
    }
}
