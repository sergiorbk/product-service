package com.sergosoft.productservice.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Order {

    Long id;
    List<OrderItem> items;

    // this microservice is going to keep the conception of P2P trading
    UUID sellerId;
    UUID buyerId;

    BigDecimal totalPrice;
    Instant createdAt;
}
