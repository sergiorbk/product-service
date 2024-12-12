package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.category.CategoryDetails;
import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.dto.product.ProductResponseDto;
import com.sergosoft.productservice.elasticsearch.document.ProductSearchDocument;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    ProductDetails toProductDetails(ProductEntity product);

    @Mapping(source = "categories", target = "categoriesIds")
    ProductResponseDto toProductResponseDto(ProductDetails product);

    ProductSearchDocument toProductDocument(ProductEntity entity);

    default String mapCategoriesToIds(CategoryDetails categoryDetails) {
        return categoryDetails != null ? categoryDetails.getSlug() : null;
    }

}