package com.sergosoft.productservice.dto.product;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ProductResponseDto {

    String productId;
    String ownerId;
    String title;
    String description;
    List<Integer> categoriesIds;
    BigDecimal price;
//    String currencyCode;
}
