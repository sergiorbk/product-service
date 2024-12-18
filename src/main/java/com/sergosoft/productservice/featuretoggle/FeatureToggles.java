package com.sergosoft.productservice.featuretoggle;

public enum FeatureToggles {
    COSMO_CATS("cosmoCats"),
    KITTY_PRODUCTS("kittyProducts");

    private final String toggleName;

    FeatureToggles(String toggleName) {
        this.toggleName = toggleName;
    }

    public String getFeatureName() {
        return toggleName;
    }
}
