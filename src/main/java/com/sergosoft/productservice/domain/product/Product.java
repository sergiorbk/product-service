package com.sergosoft.productservice.domain.product;

import java.util.List;
import java.util.UUID;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.domain.Person;
import com.sergosoft.productservice.domain.product.price.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Product {

    UUID id;
    Price price;
    Person owner;
    String title;
    String description;
    List<Category> categories;
}
