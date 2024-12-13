package com.sergosoft.productservice.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.URL;

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

    @NotBlank(message = "Category title is mandatory.")
    String title;

    String parentSlug;

    @NotNull(message = "Category image URL is mandatory.")
    @URL(message = "Invalid format of the image URL.")
    String imageUrl;

}
