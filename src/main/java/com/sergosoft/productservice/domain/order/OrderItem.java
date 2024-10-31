package com.sergosoft.productservice.domain.order;

import lombok.Builder;
import lombok.Value;

import com.sergosoft.productservice.domain.Product;

import java.math.BigDecimal;

@Value
@Builder
public class OrderItem {

    Long id;
    Order order;
    Product product;
    Integer quantity;
    BigDecimal price;
}
