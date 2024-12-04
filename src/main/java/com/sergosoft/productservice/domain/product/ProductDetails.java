package com.sergosoft.productservice.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import lombok.Value;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Value
@Builder
@AllArgsConstructor
public class ProductDetails {

    String id;
    String title;
    String description;
    UUID ownerReference;
    String slug;
    BigDecimal price;
    ProductStatus status;
    Set<CategoryDetails> categories;
    LocalDateTime createdAt;
}
