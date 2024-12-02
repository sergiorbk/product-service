package com.sergosoft.productservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.dto.order.item.OrderItemCreateDto;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import com.sergosoft.productservice.service.CategoryService;
import com.sergosoft.productservice.service.OrderService;
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
@DisplayName("Order Controller IT")
class OrderControllerIT extends IntegrationTest {

    private static UUID PRODUCT_ID;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private OrderService orderService;

    @SpyBean
    private ProductService productService;

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() throws Exception {
        reset(orderService, productService);
        PRODUCT_ID = createTestProduct();
    }

    @Test
    void shouldCreateOrder() throws Exception {
        OrderCreateDto orderCreateDto = buildOrderCreateDto(PRODUCT_ID);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice").value(1500.0));
    }

    private OrderCreateDto buildOrderCreateDto(UUID productId) {
        return OrderCreateDto.builder()
                .buyerReference(UUID.randomUUID())
                .sellerReference(UUID.randomUUID())
                .items(List.of(
                        OrderItemCreateDto.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(BigDecimal.valueOf(500))
                                .build()
                ))
                .build();
    }

    private UUID createTestProduct() throws Exception {
        ProductCreateDto productCreateDto = ProductCreateDto.builder()
                .title("Laptop")
                .price(BigDecimal.valueOf(500.0))
                .categoryIds(List.of(createTestCategory().toString()))
                .description("High-performance laptop")
                .ownerReference(UUID.randomUUID().toString())
                .build();

        String productResponse = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return UUID.fromString(objectMapper.readTree(productResponse).get("id").asText(String.valueOf(UUID.class)));
    }

    private UUID createTestCategory() throws Exception {
        CategoryCreateDto categoryCreateDto = CategoryCreateDto.builder()
                .title(UUID.randomUUID().toString())
                .build();
        return categoryService.createCategory(categoryCreateDto).getId();
    }
}
