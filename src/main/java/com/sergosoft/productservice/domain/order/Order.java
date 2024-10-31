package com.sergosoft.productservice.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class Order {

    Long id;
    List<OrderItem> orderItems;

    // this microservice is going to keep the conception of P2P trading
    UUID sellerId;
    UUID buyerId;

    Double totalPrice;
    Instant createdAt;
}
