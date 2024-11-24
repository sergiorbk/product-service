package com.sergosoft.productservice.dto.order.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class OrderItemCreationDto {

    @NotNull(message = "Order Id is mandatory.")
    Long orderId;

    @NotNull(message = "Product Id is mandatory.")
    Long productId;

    @Positive(message = "Quantity must be a positive number.")
    Integer quantity;
}
