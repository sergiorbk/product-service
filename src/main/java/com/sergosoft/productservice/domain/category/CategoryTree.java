package com.sergosoft.productservice.domain.category;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CategoryTree {

    String slug;
    String title;
    String imageUrl;
    String parentSlug;
    List<CategoryTree> subcategories;

}
