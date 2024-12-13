package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.dto.category.CategoryListDto;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "parent.slug", target = "parentSlug")
    CategoryDetails toCategoryDetails(CategoryEntity categoryEntity);

    CategoryResponseDto toCategoryResponseDto(CategoryDetails categoryDetails);

    default CategoryListDto toCategoryListDto(List<CategoryDetails> categoryDetails) {
        return new CategoryListDto(categoryDetails.stream().map(this::toCategoryResponseDto).toList());
    }

}
