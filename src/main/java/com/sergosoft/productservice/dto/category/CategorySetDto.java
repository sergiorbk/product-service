package com.sergosoft.productservice.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class CategorySetDto {

    Set<CategoryResponseDto> categories;
}
