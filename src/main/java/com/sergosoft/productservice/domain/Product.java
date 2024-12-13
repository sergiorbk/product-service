package com.sergosoft.productservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Value;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Value
@Builder
@AllArgsConstructor
public class Product {

    UUID id;
    UUID ownerId;
    String title;
    String description;
    List<Category> categories;
    BigDecimal price;
    Instant createdAt;
//    CurrencyRate currency;
}
