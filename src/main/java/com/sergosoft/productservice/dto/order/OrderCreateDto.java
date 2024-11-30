package com.sergosoft.productservice.dto.order;

import com.sergosoft.productservice.dto.order.item.OrderItemCreateDto;
import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class OrderCreateDto {

    String sellerReference;
    String buyerReference;
    List<OrderItemCreateDto> items;
    Double totalPrice;
}
