package com.sergosoft.productservice.dto.category;

import com.sergosoft.productservice.domain.category.CategoryStatus;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryUpdateDto {

    String title;
    String parentSlug;
    CategoryStatus status;
    String imageUrl;

}
