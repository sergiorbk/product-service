package com.sergosoft.productservice.dto.category.response;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategorySlimDto {

    String slug;
    String title;
    String parentSlug;
    String imageUrl;

}
