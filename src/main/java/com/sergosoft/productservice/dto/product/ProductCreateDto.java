package com.sergosoft.productservice.dto.product;

import java.util.List;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.UUID;

@Value
@Builder
@Jacksonized
public class ProductCreateDto {

    @NotBlank(message = "Title is mandatory.")
    String title;

    @NotBlank(message = "Description is mandatory.")
    String description;

    @NotNull(message = "Owner reference is mandatory for product")
    @UUID(message = "Invalid format of ownerReferenceId")
    String ownerReference;

    @NotEmpty(message = "Categories ids list must contain at least 1 category id.")
    List<@NotNull(message = "Category id cannot be null")
         @UUID(message = "Invalid format of category id") String> categoryIds;

    @Positive(message = "Price must be a positive number.")
    BigDecimal price;
}
