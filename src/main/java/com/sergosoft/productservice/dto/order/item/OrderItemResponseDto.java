package com.sergosoft.productservice.dto.order.item;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class OrderItemResponseDto {

    UUID id;
    UUID orderId;
    UUID productId;
    Integer quantity;
    BigDecimal price;
}
