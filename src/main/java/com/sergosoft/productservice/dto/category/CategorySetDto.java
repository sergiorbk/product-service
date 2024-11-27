package com.sergosoft.productservice.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@AllArgsConstructor
public class CategorySetDto {

    Set<CategoryResponseDto> categories;
}
