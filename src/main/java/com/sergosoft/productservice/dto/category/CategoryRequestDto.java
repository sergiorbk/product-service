package com.sergosoft.productservice.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

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
public class CategoryRequestDto {

    /**
     * The title or name of the category.
     * <p>
     * This field is mandatory, as every category requires a title.
     * </p>
     */
    @NotNull(message = "Category is mandatory")
    @NotBlank(message = "Category title is mandatory.")
    String title;

    /**
     * The ID of the parent category, if this category is a subcategory.
     * <p>
     * If this value is provided, it should reference an existing category ID.
     * If null, the category will be created as a root category.
     * </p>
     *
     * @implNote This field is optional.
     */
    @NotNull(message = "Category parent id is mandatory.")
    @Positive(message = "Category parent id must be positive.")
    Long parentId;
}
