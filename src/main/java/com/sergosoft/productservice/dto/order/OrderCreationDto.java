package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemCreateDto;
import lombok.Value;
import lombok.Builder;

import java.util.List;

@Value
@Builder
public class OrderCreationDto {

    String sellerReference;
    String buyerReference;
    List<OrderItemCreateDto> items;
    Double totalPrice;
}
