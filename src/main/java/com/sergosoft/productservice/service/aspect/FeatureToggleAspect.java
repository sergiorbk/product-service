package com.sergosoft.productservice.service.aspect;

import com.sergosoft.productservice.service.FeatureToggleService;
import com.sergosoft.productservice.service.exception.FeatureNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Before("execution(* com.sergosoft.productservice.service.CosmoCatService.getCosmoCats(..))")
    public void checkCosmoCatsFeatureToggle() {
        if(!featureToggleService.isCosmoCatsEnabled()) {
            throw new FeatureNotAvailableException("Cosmo Cats feature is disabled");
        }
    }
}
