package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.repository.faker.FakeCrudRepository;

public interface CategoryRepository extends FakeCrudRepository<Category, Integer> {
    // todo implement as a JPA repository
}
