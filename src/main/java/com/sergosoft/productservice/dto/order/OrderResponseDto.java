package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemResponseDto;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderResponseDto {

    UUID id;
    UUID sellerReference;
    UUID buyerReference;
    List<OrderItemResponseDto> items;
    BigDecimal totalPrice;
    LocalDateTime createdAt;
}
