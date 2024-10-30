package com.sergosoft.productservice.domain.product.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor
public class Price {

    BigDecimal amount;
    Currency currency;
}
