package com.sergosoft.productservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {

    @Id
    String currencyCode;
    BigDecimal exchangeRate;
}
