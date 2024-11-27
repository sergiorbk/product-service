package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "parent.id", target = "parentId")
    CategoryDetails toCategoryDetails(CategoryEntity categoryEntity);


    CategoryResponseDto toCategoryDetailsDto(CategoryDetails categoryDetails);
}
