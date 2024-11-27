package com.sergosoft.productservice.domain.order.item;

import com.sergosoft.productservice.domain.order.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import com.sergosoft.productservice.domain.Product;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class OrderItem {

    Long id;
    OrderDetails orderDetails;
    Product product;
    Integer quantity;
    BigDecimal price;
}
