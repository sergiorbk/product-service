package com.sergosoft.productservice.dto.order.item;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

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
