package com.sergosoft.productservice.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CurrencyRate {

    String currencyCode;
    BigDecimal exchangeRate;
}
