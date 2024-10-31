package com.sergosoft.productservice.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderItemDto {

    @NotNull(message = "Product Id is mandatory.")
    UUID productId;

    @Positive(message = "Quantity must be a positive number.")
    Integer quantity;
}
