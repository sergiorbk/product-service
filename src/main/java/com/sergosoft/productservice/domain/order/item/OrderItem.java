package com.sergosoft.productservice.domain.order.item;

import com.sergosoft.productservice.domain.order.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import com.sergosoft.productservice.domain.product.ProductDetails;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class OrderItem {

    UUID id;
    OrderDetails orderDetails;
    ProductDetails productDetails;
    Integer quantity;
    BigDecimal price;
}
