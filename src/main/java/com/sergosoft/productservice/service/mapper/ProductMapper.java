package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductCreateDto;
import com.sergosoft.productservice.dto.product.ProductResponseDto;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "base64Id", target = "id")
    ProductDetails toProductDetails(ProductEntity product);

    ProductEntity toProductEntity(ProductCreateDto productCreateDto);

    @Mapping(source = "categories", target = "categoriesIds")
    ProductResponseDto toProductResponseDto(ProductDetails product);

    default String map(CategoryDetails categoryDetails) {
        return categoryDetails != null ? categoryDetails.getId().toString() : null;
    }

}