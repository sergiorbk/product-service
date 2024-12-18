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
@Builder(toBuilder = true)
@Jacksonized
public class ProductCreateDto {

    @NotNull(message = "Product title is mandatory.")
    @NotBlank(message = "Product title cannot be a blank line.")
    String title;

    @NotNull(message = "Product description is mandatory.")
    @NotBlank(message = "Description cannot be a blank line.")
    String description;

    @NotNull(message = "Owner reference is mandatory for product")
    @UUID(message = "Invalid format of ownerReferenceId")
    String ownerReference;

    @NotEmpty(message = "Categories ids list must contain at least 1 category id.")
    List<@NotBlank(message = "Category id cannot be blank.") String> categoryIds;

    @Positive(message = "Price must be a positive number.")
    BigDecimal price;

}
