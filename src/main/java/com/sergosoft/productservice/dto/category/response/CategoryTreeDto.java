package com.sergosoft.productservice.dto.category.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CategoryTreeDto {

    String slug;
    String title;
    String imageUrl;
    String parentSlug;
    List<CategoryTreeDto> subcategories;

}
