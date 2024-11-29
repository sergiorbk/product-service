package com.sergosoft.productservice.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ProductUpdateDto {

    @NotBlank(message = "Title is mandatory.")
    String title;

    @NotBlank(message = "Description is mandatory.")
    String description;

    @NotEmpty(message = "Categories ids list must contain at least 1 category id.")
    List<@NotNull(message = "Category id cannot be null")
         @UUID(message = "Invalid format of category id") String> categoryIds;

    @Positive(message = "Price must be a positive number.")
    BigDecimal price;

}
