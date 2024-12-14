package com.sergosoft.productservice.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class ProductUpdateDto {

    String title;

    String description;

//    @NotEmpty(message = "Categories ids list must contain at least 1 category id.")
    List<@NotNull(message = "Category id cannot be null")
         @UUID(message = "Invalid format of category id") String> categoryIds;

    @Positive(message = "Price must be a positive number.")
    BigDecimal price;

}