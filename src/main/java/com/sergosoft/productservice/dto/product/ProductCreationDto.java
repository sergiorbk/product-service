package com.sergosoft.productservice.dto.product;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import lombok.Value;
import lombok.Builder;

@Value
@Builder
public class ProductCreationDto {

    @NotBlank(message = "Title is mandatory.")
    String title;

    @NotBlank(message = "Description is mandatory.")
    String description;

    @NotEmpty(message = "Categories ids list must contain at least 1 category id.")
    Set<Integer> categoriesIds;

    @Positive(message = "Price must be a positive number.")
    BigDecimal price;
}
