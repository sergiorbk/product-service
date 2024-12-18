package com.sergosoft.productservice.featuretoggle;

public enum FeatureToggles {
    EXAMPLE_TOGGLE("exampleToggle");

    private final String toggleName;

    FeatureToggles(String toggleName) {
        this.toggleName = toggleName;
    }

    public String getFeatureName() {
        return toggleName;
    }
}
