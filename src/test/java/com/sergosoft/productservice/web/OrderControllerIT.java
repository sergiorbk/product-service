package com.sergosoft.productservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergosoft.productservice.IntegrationTest;
import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.dto.order.OrderCreationDto;
import com.sergosoft.productservice.dto.order.OrderResponseDto;
import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Order Controller IT")
@Tag("order-service")
class OrderControllerIT extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    private Order order;
    private OrderCreationDto orderCreationDto;
    private OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {
        UUID sellerId = UUID.randomUUID();
        UUID buyerId = UUID.randomUUID();
        Long orderId = 1L;

        order = Order.builder()
                .id(orderId)
                .sellerId(sellerId)
                .buyerId(buyerId)
                .createdAt(Instant.now())
                .items(List.of())
                .totalPrice(BigDecimal.valueOf(500))
                .build();

        orderCreationDto = OrderCreationDto.builder()
                .sellerId(sellerId)
                .buyerId(buyerId)
                .items(List.of())
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .id(order.getId())
                .sellerId(order.getSellerId())
                .buyerId(order.getBuyerId())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .items(List.of())
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/orders/{id} - Success")
    void getOrderById_ShouldReturnOrder() throws Exception {
        when(orderService.getOrderById(order.getId())).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderResponseDto);

        mockMvc.perform(get("/api/v1/orders/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.sellerId").value(orderResponseDto.getSellerId().toString()))
                .andExpect(jsonPath("$.buyerId").value(orderResponseDto.getBuyerId().toString()))
                .andExpect(jsonPath("$.totalPrice").value(orderResponseDto.getTotalPrice()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("POST /api/v1/orders - Success")
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        when(orderService.createOrder(any(OrderCreationDto.class))).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderResponseDto);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreationDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.sellerId").value(orderResponseDto.getSellerId().toString()))
                .andExpect(jsonPath("$.buyerId").value(orderResponseDto.getBuyerId().toString()))
                .andExpect(jsonPath("$.totalPrice").value(orderResponseDto.getTotalPrice()));
    }

    @Test
    @DisplayName("PUT /api/v1/orders/{id} - Success")
    void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        when(orderService.updateOrder(order.getId(), orderCreationDto)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderResponseDto);

        mockMvc.perform(put("/api/v1/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreationDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.sellerId").value(orderResponseDto.getSellerId().toString()))
                .andExpect(jsonPath("$.buyerId").value(orderResponseDto.getBuyerId().toString()))
                .andExpect(jsonPath("$.totalPrice").value(orderResponseDto.getTotalPrice()));
    }

    @Test
    @DisplayName("DELETE /api/v1/orders/{id} - Success")
    void deleteOrder_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrderById(order.getId());

        mockMvc.perform(delete("/api/v1/orders/{id}", order.getId()))
                .andExpect(status().isNoContent());
    }
}
