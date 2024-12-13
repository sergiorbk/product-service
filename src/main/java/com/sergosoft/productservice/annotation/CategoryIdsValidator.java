package com.sergosoft.productservice.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CategoryIdsValidator implements ConstraintValidator<ValidCategoryIds, List<Integer>> {

    @Override
    public boolean isValid(List<Integer> categoriesIds, ConstraintValidatorContext context) {
        if (categoriesIds == null || categoriesIds.isEmpty()) {
            return true;
        }

        for (Integer id : categoriesIds) {
            if (id == null || id <= 0) {
                context.buildConstraintViolationWithTemplate("Each category ID must be a positive number.")
                        .addPropertyNode("categoriesIds")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
