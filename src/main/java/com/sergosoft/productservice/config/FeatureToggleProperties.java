package com.sergosoft.productservice.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.feature")
public class FeatureToggleProperties {

    Map<String, Boolean> toggles = new ConcurrentHashMap<>();

    public boolean check(String featureToggle){
        return toggles.getOrDefault(featureToggle, false);
    }

}
