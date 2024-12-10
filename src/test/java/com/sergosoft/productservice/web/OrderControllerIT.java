package com.sergosoft.productservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.dto.order.item.OrderItemCreateDto;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.reset;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Order Controller IT")
class OrderControllerIT extends IntegrationTest {

    private CategoryDetails testElectronicsCategory;
    private CategoryDetails testLaptopsCategory;
    private ProductDetails testLaptopProduct;
    private ProductDetails testFridgeProduct;
    private OrderDetails testOrderDetails;
    private List<ProductDetails> existentProducts;

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
    void setUp() {
        reset(orderService, productService, categoryService);
        // create categories
        testElectronicsCategory = categoryService.createCategory(CategoryControllerIT.buildCreateCategoryDto(null));
        testLaptopsCategory = categoryService.createCategory(CategoryControllerIT.buildCreateCategoryDto(List.of(testElectronicsCategory.getId().toString())));
        // create products
        testLaptopProduct = productService.createProduct(ProductControllerIT.buildProductCreateDto(List.of(testLaptopsCategory.getId().toString(), testElectronicsCategory.getId().toString())));
        testFridgeProduct = productService.createProduct(ProductControllerIT.buildProductCreateDto(List.of(testElectronicsCategory.getId().toString())));
        existentProducts = new ArrayList<>(List.of(testLaptopProduct, testFridgeProduct));
        // create orders
        testOrderDetails = orderService.createOrder(buildOrderCreateDto(existentProducts.stream().map(ProductDetails::getId).map(UUID::fromString).toList()));
    }

    @Test
    void shouldGetOrderById() throws Exception {
        mockMvc.perform(get("/api/v1/orders/{id}", testOrderDetails.getId())
                .with(jwt().jwt(jwt -> jwt
                        .claim("sub", "mock-user")
                        .claim("email", "mockuser@example.com")
                        .claim("roles", "USER")))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrderDetails.getId().toString()));
    }

    @Test
    void shouldGetOrderByIdUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/orders/{id}", testOrderDetails.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateOrder() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildOrderCreateDto(existentProducts.stream().map(ProductDetails::getId).map(UUID::fromString).toList()))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldUpdateOrder() throws Exception {
        List<ProductDetails> updatedProducts = new ArrayList<>(List.copyOf(existentProducts));
        updatedProducts.add(testFridgeProduct);

        OrderCreateDto orderUpdateDto = buildOrderCreateDto(updatedProducts.stream().map(ProductDetails::getId).map(UUID::fromString).toList());
        BigDecimal expectedTotalPrice = existentProducts.stream().map(ProductDetails::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // create a new order to update
        OrderCreateDto orderToSaveDto = buildOrderCreateDto(existentProducts.stream().map(ProductDetails::getId).map(UUID::fromString).toList());
        OrderDetails orderToUpdate = orderService.createOrder(orderToSaveDto);

        mockMvc.perform(put("/api/v1/orders/{id}", orderToUpdate.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyerReference").value(orderUpdateDto.getBuyerReference().toString()))
                .andExpect(jsonPath("$.sellerReference").value(orderUpdateDto.getSellerReference().toString()));
//                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice));

        // deleting updated order if all tests are passed
        orderService.deleteOrderById(orderToUpdate.getId());
    }

    @Test
    @WithMockUser
    void shouldDeleteOrder() throws Exception {
        OrderCreateDto orderToSaveDto = buildOrderCreateDto(existentProducts.stream().map(ProductDetails::getId).map(UUID::fromString).collect(Collectors.toList()));
        OrderDetails orderToDelete = orderService.createOrder(orderToSaveDto);

        // check if order was created
        mockMvc.perform(get("/api/v1/orders/{id}", orderToDelete.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderToDelete.getId().toString()));

        // check if order was deleted
        mockMvc.perform(delete("/api/v1/orders/{id}", orderToDelete.getId()))
                .andExpect(status().isNoContent());

        // check if order does not exist
        mockMvc.perform(get("/api/v1/orders/{id}", orderToDelete.getId()))
                .andExpect(status().isNotFound());
    }

    private OrderCreateDto buildOrderCreateDto(List<UUID> productsIds) {
        List<OrderItemCreateDto> items = new ArrayList<>();
        for(UUID productId : productsIds) {
            OrderItemCreateDto item = OrderItemCreateDto.builder()
                    .productId(productId)
                    .quantity(RandomUtils.nextInt(1, 50))
                    .build();
            items.add(item);
        }
        return OrderCreateDto.builder()
                .buyerReference(UUID.randomUUID())
                .sellerReference(UUID.randomUUID())
                .items(items)
                .build();
    }
}
