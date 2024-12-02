package com.sergosoft.productservice.dto.order.item;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class OrderItemCreateDto {
    private UUID productId;
    private Integer quantity;
    private BigDecimal price;
}