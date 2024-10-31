package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemResponseDto;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderResponseDto {

    Long id;
    UUID sellerId;
    UUID buyerId;
    List<OrderItemResponseDto> items;
    Double totalPrice;
    Instant createdAt;
}
