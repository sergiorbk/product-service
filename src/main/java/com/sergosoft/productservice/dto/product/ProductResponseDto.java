package com.sergosoft.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
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
    LocalDateTime createdAt;

}
