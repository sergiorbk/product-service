package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.category.CategoryTree;
import com.sergosoft.productservice.dto.category.response.CategoryTreeDto;
import com.sergosoft.productservice.dto.category.response.CategorySlimDto;
import com.sergosoft.productservice.dto.category.response.CategorySlimDtoList;
import com.sergosoft.productservice.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDetails toCategoryDetails(CategoryEntity categoryEntity);

    @Mapping(source = "parent.slug", target = "parentSlug")
    CategorySlimDto toCategorySlimDto(CategoryDetails categoryDetails);

    CategoryTreeDto toCategoryTreeDto(CategoryTree categoryTree);

    @Mapping(source = "parent.slug", target = "parentSlug")
    CategoryTree toCategoryTree(CategoryEntity categoryEntity);

    default CategorySlimDtoList toCategorySlimDtoList(List<CategoryDetails> categoryDetails) {
        return new CategorySlimDtoList(categoryDetails.stream().map(this::toCategorySlimDto).toList());
    }

}
