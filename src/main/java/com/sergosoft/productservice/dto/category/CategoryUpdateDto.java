package com.sergosoft.productservice.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryUpdateDto {

    @NotBlank(message = "Category title is mandatory.")
    String title;

    @Positive(message = "Category parent id must be positive.")
    Long parentId;
}
