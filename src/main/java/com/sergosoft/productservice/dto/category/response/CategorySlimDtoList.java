package com.sergosoft.productservice.dto.category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class CategorySlimDtoList {

    List<CategorySlimDto> categories;

}
