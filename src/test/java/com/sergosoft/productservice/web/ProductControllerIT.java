package com.sergosoft.productservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
class ProductControllerIT extends IntegrationTest {

    private static UUID CATEGORY_ID;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private ProductService productService;

    @BeforeEach
    void setUp() throws Exception {
        reset(productService);
        if(CATEGORY_ID != null) {
            productService.deleteProductById(CATEGORY_ID);
        }
        CATEGORY_ID = createTestCategory();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductCreateDto productCreateDto = buildProductCreateDto(CATEGORY_ID);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Laptop"))
                .andExpect(jsonPath("$.price").value(500.0));
    }

    @Test
    void shouldReturnBadRequestIfTitleIsMissing() throws Exception {
        ProductCreateDto productCreateDto = buildProductCreateDto(CATEGORY_ID).toBuilder()
                .title("")
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isBadRequest());
    }

    private ProductCreateDto buildProductCreateDto(UUID categoryId) {
        return ProductCreateDto.builder()
                .title("Laptop")
                .ownerReference(UUID.randomUUID().toString())
                .price(BigDecimal.valueOf(500.0))
                .categoryIds(List.of(categoryId.toString()))
                .description("High-performance laptop")
                .build();
    }

    private UUID createTestCategory() throws Exception {
        CategoryCreateDto categoryCreateDto = CategoryCreateDto.builder()
                .title(UUID.randomUUID().toString())
                .parentId(null)
                .build();

        String categoryResponse = mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryCreateDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return UUID.fromString(objectMapper.readTree(categoryResponse).get("id").asText());
    }
}
