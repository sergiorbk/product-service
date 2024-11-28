package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "base64Id", target = "id")

    ProductDetails toProductDetails(ProductEntity product);
}
