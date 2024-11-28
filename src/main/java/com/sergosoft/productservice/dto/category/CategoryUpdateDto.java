package com.sergosoft.productservice.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.UUID;

@Value
@Builder
public class CategoryUpdateDto {

    @NotBlank(message = "Category title is mandatory.")
    String title;

    @UUID(message = "Category parent id bust be UUID.")
    String parentId;

    @NotBlank(message = "Category status is mandatory.")
    String status;
}