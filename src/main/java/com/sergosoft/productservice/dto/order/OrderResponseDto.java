package com.sergosoft.productservice.dto.order;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OrderResponseDto {

    UUID id;
    UUID sellerId;
    UUID buyerId;
    List<OrderItemDto> items;
    Double totalPrice;
    Instant createdAt;
}
