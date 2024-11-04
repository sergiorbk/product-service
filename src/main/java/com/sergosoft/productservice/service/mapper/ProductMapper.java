package com.sergosoft.productservice.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "categoriesIds", source = "categories")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "createdAt", source = "createdAt")
    ProductResponseDto toDto(Product product);

    default Set<Integer> map(Set<Category> categories) {
        if(categories == null) {
            return null;
        }
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }
}
