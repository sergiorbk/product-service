package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.service.FeatureToggleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleServiceImpl implements FeatureToggleService {

   @Value("${feature.cosmoCats.enabled}")
    private boolean cosmoCatsEnabled;

    @Value("${feature.kittyProducts.enabled}")
    private boolean kittyProductsEnabled;

    @Value("${feature.currencyRates.enabled}")
    private boolean currencyRatesEnabled;

    @Override
    public boolean isCosmoCatsEnabled() {
        return cosmoCatsEnabled;
    }

    @Override
    public boolean isKittyProductsEnabled() {
        return kittyProductsEnabled;
    }

    @Override
    public boolean isCurrencyRatesEnabled() {
        return currencyRatesEnabled;
    }
}
