package com.sergosoft.productservice.dto.category;

import com.sergosoft.productservice.domain.category.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryUpdateDto {

    @NotBlank(message = "Category title is mandatory.")
    String title;

//    @NotEmpty(message = "Category parent slug cannot be an empty string.")
    String parentSlug;

    CategoryStatus status;
}
