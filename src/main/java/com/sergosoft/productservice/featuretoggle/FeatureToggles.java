package com.sergosoft.productservice.featuretoggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {
    COSMO_CATS("cosmoCats"),
    KITTY_PRODUCTS("kittyProducts"),
    CURRENCY_RATES("kurrencyRates");

    private final String toggleName;

    FeatureToggles(String toggleName) {
        this.toggleName = toggleName;
    }
}
