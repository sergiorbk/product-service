package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.service.FeatureToggleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleServiceImpl implements FeatureToggleService {

   @Value("${feature.kittyProducts.enabled:false}")
    private boolean cosmoCatsEnabled;

    @Value("${feature.kittyProducts.enabled:false}")
    private boolean kittyProductsEnabled;

    @Override
    public boolean isCosmoCatsEnabled() {
        return cosmoCatsEnabled;
    }

    @Override
    public boolean isKittyProductsEnabled() {
        return kittyProductsEnabled;
    }
}
