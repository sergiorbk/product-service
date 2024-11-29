package com.sergosoft.productservice.domain.order;

import com.sergosoft.productservice.domain.order.item.OrderItemDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class OrderDetails {

    UUID id;
    Set<OrderItemDetails> items = new HashSet<>();

    // this microservice is going to keep the conception of P2P trading
    UUID sellerId;
    UUID buyerId;

    BigDecimal totalPrice;
    Instant createdAt;
}
