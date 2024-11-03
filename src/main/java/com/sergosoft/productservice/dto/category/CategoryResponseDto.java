package com.sergosoft.productservice.dto.category;

import lombok.Value;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryResponseDto {

    Integer id;
    String title;
    Integer parentId;
}
