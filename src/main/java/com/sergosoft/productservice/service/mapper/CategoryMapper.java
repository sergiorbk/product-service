package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;
import com.sergosoft.productservice.dto.category.CategorySetDto;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "parent.id", target = "parentId")
    CategoryDetails toCategoryDetails(CategoryEntity categoryEntity);

    CategoryResponseDto toCategoryResponseDto(CategoryDetails categoryDetails);

    default CategorySetDto toCategorySetDto(Set<CategoryDetails> categoryDetails) {
        Set<CategoryResponseDto> responses = categoryDetails.stream()
                .map(this::toCategoryResponseDto)
                .collect(Collectors.toSet());
        return new CategorySetDto(responses);
    }

}
