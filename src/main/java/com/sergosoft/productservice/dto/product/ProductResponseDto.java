package com.sergosoft.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class ProductResponseDto {

    String productId;
    String ownerReference;
    String title;
    String description;
    List<Long> categoriesIds;
    BigDecimal price;
    String status;
    Instant createdAt;
//    String currencyCode;
}
