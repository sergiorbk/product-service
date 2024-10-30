package com.sergosoft.productservice.dto.category;

import com.sergosoft.productservice.domain.Category;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryResponseDto {

    Integer id;
    String title;
    Integer parentId;
}
