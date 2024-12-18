package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductResponseDto;
import com.sergosoft.productservice.dto.product.ProductUpdateDto;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    ProductDetails toProductDetails(ProductEntity product);

    @Mapping(source = "categories", target = "categoriesIds")
    ProductResponseDto toProductResponseDto(ProductDetails product);

    @Mapping(target = "slug", expression = "java(com.sergosoft.productservice.util.SlugGenerator.generateSlug(dto.getTitle()))")
    void updateProductFromDto(ProductUpdateDto dto, @MappingTarget ProductEntity entity);

    default String mapCategoriesToIds(CategoryDetails categoryDetails) {
        return categoryDetails != null ? categoryDetails.getSlug() : null;
    }

}