package com.sergosoft.productservice.dto.category;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryResponseDto {

    Long id;
    String title;
    String slug;
    Long parentId;
}
