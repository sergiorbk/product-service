package com.sergosoft.productservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import com.sergosoft.productservice.dto.product.ProductUpdateDto;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
class ProductControllerIT extends IntegrationTest {

    private CategoryDetails testCategoryDetails;
    private List<String> categoryIds;
    private ProductDetails testProductDetails;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private ProductService productService;

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        reset(productService, categoryService);
        testCategoryDetails = categoryService.createCategory(CategoryCreateDto.builder()
                .title("Test Category " + RandomStringUtils.randomAlphabetic(5))
                .build());
        categoryIds = List.of(testCategoryDetails.getId().toString());
        testProductDetails = productService.createProduct(buildProductCreateDto(categoryIds));
    }

    @Test
    void shouldGetProductById() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}", testProductDetails.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value(testProductDetails.getTitle()))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description").value(testProductDetails.getDescription()))
                .andExpect(jsonPath("$.price").exists())
                .andExpect(result -> {
                    Double actualPrice = JsonPath.read(result.getResponse().getContentAsString(), "$.price");
                    assertEquals(testProductDetails.getPrice().doubleValue(), actualPrice);
                })
                .andExpect(jsonPath("$.ownerReference").exists())
                .andExpect(jsonPath("$.ownerReference").value(testProductDetails.getOwnerReference().toString()))
                .andExpect(jsonPath("$.categoriesIds").exists())
                .andExpect(jsonPath("$.categoriesIds").isArray());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductCreateDto productCreateDto = buildProductCreateDto(categoryIds);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(productCreateDto.getTitle()))
                .andExpect(jsonPath("$.price").value(productCreateDto.getPrice()));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
//        CategoryCreateDto categoryCreateDto = CategoryCreateDto.builder()
//                .title(testCategoryDetails.getTitle() + " child " + RandomStringUtils.randomAlphabetic(5))
//                .parentId(testCategoryDetails.getId().toString())
//                .build();
//        CategoryDetails childCategoryDetails = categoryService.createCategory(categoryCreateDto);

        ProductUpdateDto productUpdateDto = ProductUpdateDto.builder()
                .title(RandomStringUtils.randomAlphabetic(5))
                .description(RandomStringUtils.randomAlphabetic(5))
                .price(BigDecimal.valueOf(888))
//                .categoryIds(List.of(childCategoryDetails.getId().toString()))
                .build();

        mockMvc.perform(put("/api/v1/products/{id}", testProductDetails.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productUpdateDto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(productUpdateDto.getTitle()))
                .andExpect(jsonPath("$.description").value(productUpdateDto.getDescription()))
                .andExpect(jsonPath("$.price").value(productUpdateDto.getPrice()));
//                .andExpect(jsonPath("$.categoriesIds").exists())
//                .andExpect(jsonPath("$.categoriesIds").isArray());
    }

    @Test
    void shouldReturnBadRequestIfTitleIsMissing() throws Exception {
        ProductCreateDto productCreateDto = buildProductCreateDto(categoryIds).toBuilder()
                .title("")
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteProductById() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}", testProductDetails.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/v1/products/{id}", testProductDetails.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/products/{id}", testProductDetails.getId()))
                .andExpect(status().isNotFound());
    }

    ProductCreateDto buildProductCreateDto(List<String> categoryIds) {
        return ProductCreateDto.builder()
                .title("Test Product " + RandomStringUtils.randomAlphabetic(10))
                .description("Test Description " + RandomStringUtils.randomAlphabetic(10))
                .ownerReference(UUID.randomUUID().toString())
                .categoryIds(categoryIds)
                .price(BigDecimal.valueOf(777))
                .build();
    }
}
