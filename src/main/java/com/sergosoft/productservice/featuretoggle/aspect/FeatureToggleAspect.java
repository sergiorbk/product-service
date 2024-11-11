package com.sergosoft.productservice.featuretoggle.aspect;

import com.sergosoft.productservice.featuretoggle.FeatureToggleService;
import com.sergosoft.productservice.featuretoggle.FeatureToggles;
import com.sergosoft.productservice.featuretoggle.annotation.FeatureToggle;
import com.sergosoft.productservice.featuretoggle.exception.FeatureNotAvailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around(value = "@annotation(featureToggle)")
    public Object checkFeatureToggleAnnotation(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        return checkToggle(joinPoint, featureToggle);
    }

    private Object checkToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        FeatureToggles toggle = featureToggle.value();

        if (featureToggleService.check(toggle.getFeatureName())) {
            return joinPoint.proceed();
        }
        String featureToggleNotEnabledMessage = String.format("Feature toggle %s is not enabled!", toggle.getFeatureName());
        log.warn(featureToggleNotEnabledMessage);
        throw new FeatureNotAvailableException(String.format(featureToggleNotEnabledMessage));
    }
}
