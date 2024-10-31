package com.sergosoft.productservice.domain.order;

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
    Order order;
    Product product;
    Integer quantity;
    BigDecimal price;
}
