package com.sergosoft.productservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", expression = "java(entity.getParent() != null ? entity.getParent().getId() : null)")
    CategoryResponseDto toDto(Category entity);
}
