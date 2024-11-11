package com.sergosoft.productservice.featuretoggle;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class FeatureToggleService {

   @Value("${application.feature.cosmoCats.enabled}")
    private boolean cosmoCatsEnabled;

    @Value("${application.feature.kittyProducts.enabled}")
    private boolean kittyProductsEnabled;

    @Value("${application.feature.currencyRates.enabled}")
    private boolean currencyRatesEnabled;
}
