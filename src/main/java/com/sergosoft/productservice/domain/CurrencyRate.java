package com.sergosoft.productservice.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CurrencyRate {

    String currencyCode;
    BigDecimal exchangeRate;
}
