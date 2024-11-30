package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemCreateDto;
import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderCreateDto {

    UUID sellerReference;
    UUID buyerReference;
    List<OrderItemCreateDto> items;
    BigDecimal totalPrice;
}
