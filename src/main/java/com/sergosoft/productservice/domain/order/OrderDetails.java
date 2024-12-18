package com.sergosoft.productservice.domain.order;

import com.sergosoft.productservice.domain.order.item.OrderItemDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class OrderDetails {

    UUID id;
    List<OrderItemDetails> items;
    UUID sellerReference;
    UUID buyerReference;
    BigDecimal totalPrice;
    OffsetDateTime createdAt;
}
