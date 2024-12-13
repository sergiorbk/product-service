package com.sergosoft.productservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CategoryIdsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategoryIds {
    String message() default "Categories IDs must contain only positive numbers.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
