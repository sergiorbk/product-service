package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemCreationDto;
import lombok.Value;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OrderCreationDto {

    UUID sellerId;
    UUID buyerId;
    List<OrderItemCreationDto> items;
    Double totalPrice;
}
