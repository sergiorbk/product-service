package com.sergosoft.productservice.validation.price;

import com.sergosoft.productservice.config.PriceConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class PriceValidator implements ConstraintValidator<Price, BigDecimal> {

    private final PriceConfig priceConfig;

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null || value.compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }
        return value.precision() <= priceConfig.getPrecision() && value.scale() <= priceConfig.getScale();
    }
}
