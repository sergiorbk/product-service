package com.sergosoft.productservice.dto.order.item;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class OrderItemResponseDto {

    String id;
    String orderId;
    String productId;
    Integer quantity;
    BigDecimal price;
}
