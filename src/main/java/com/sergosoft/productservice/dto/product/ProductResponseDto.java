package com.sergosoft.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ProductResponseDto {

    String id;
    String title;
    String description;
    String ownerReference;
    String slug;
    BigDecimal price;
    String status;
    Set<String> categoriesIds;
    OffsetDateTime createdAt;

}
