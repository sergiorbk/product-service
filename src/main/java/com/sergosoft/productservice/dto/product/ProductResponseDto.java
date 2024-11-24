package com.sergosoft.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class ProductResponseDto {

    Long productId;
    UUID ownerId;
    String title;
    String description;
    List<Long> categoriesIds;
    BigDecimal price;
    Instant createdAt;
//    String currencyCode;
}
