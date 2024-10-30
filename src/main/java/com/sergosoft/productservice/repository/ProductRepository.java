package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.repository.faker.FakeCrudRepository;

import java.util.UUID;

public interface ProductRepository extends FakeCrudRepository<Product, UUID> {
    // todo implement as a JPA repository
}
