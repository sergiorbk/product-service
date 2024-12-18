package com.sergosoft.productservice.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.UUID;

/**
 * Data Transfer Object for creating a new category of product.
 * <p>
 * This DTO is used to transfer data needed to create a new category, either as a root category
 * or as a subcategory of an existing category. It includes a title for the new category and an
 * optional parent category ID if it should be a subcategory.
 * </p>
 */
@Value
@Builder
@Jacksonized
public class CategoryCreateDto {

    @NotNull(message = "Category is mandatory")
    @NotBlank(message = "Category title is mandatory.")
    String title;

    @UUID(message = "Category parent id bust be UUID.")
    String parentId;
}
