package com.sergosoft.productservice.service.mapper;

import org.springframework.stereotype.Component;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.dto.category.CategoryResponseDto;

@Component
public class CategoryMapper {

    public CategoryResponseDto toDto(Category entity) {
        if (entity == null) {
            return null;
        }

        return CategoryResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
                .build();
    }

    public Category toEntity(CategoryResponseDto dto) {
        if (dto == null) {
            return null;
        }

        Category.CategoryBuilder builder = Category.builder()
                .id(dto.getId())
                .title(dto.getTitle());

        if (dto.getParentId() != null) {
            builder.parent(Category.builder().id(dto.getParentId()).build());
        }

        return builder.build();
    }

    public Category toEntity(CategoryCreateDto dto) {
        if (dto == null) {
            return null;
        }

        Category.CategoryBuilder builder = Category.builder()
                .title(dto.getTitle());

        if (dto.getParentId() != null) {
            builder.parent(Category.builder().id(dto.getParentId()).build());
        }

        return builder.build();
    }
}
