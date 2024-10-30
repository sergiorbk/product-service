package com.sergosoft.productservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponseDto toDto(Category entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "parent.id", source = "parentId")
    Category toEntity(CategoryResponseDto dto);
}
