package com.sergosoft.productservice.dto.order.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderItemCreateDto {

    @NotNull(message = "Product ID must not be null")
    UUID productId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    BigDecimal price;
}