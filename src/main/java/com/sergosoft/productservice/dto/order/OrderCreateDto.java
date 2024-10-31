package com.sergosoft.productservice.dto.order;

import lombok.Value;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OrderCreateDto {

    UUID sellerId;
    UUID buyerId;
    List<OrderItemDto> items;
    Double totalPrice;
}
