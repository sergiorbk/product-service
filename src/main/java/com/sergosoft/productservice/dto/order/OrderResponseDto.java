package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemResponseDto;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
@Jacksonized
public class OrderResponseDto {

    String id;
    List<OrderItemResponseDto> items;
    String sellerReference;
    String buyerReference;
    BigDecimal totalPrice;
    OffsetDateTime createdAt;

}
