package com.sergosoft.productservice.repository.faker.impl;

import java.util.UUID;

import com.sergosoft.productservice.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.repository.faker.FakeRepository;

/**
 * Fake repository of Product. Simulates CRUD operations on Product domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 */
@Repository
@Profile("fakeRepository")
//@Deprecated(forRemoval = true)
public class ProductFakeRepository extends FakeRepository<Product, UUID> implements ProductRepository {

    public ProductFakeRepository() {
        lastId = UUID.randomUUID();
    }

    @Override
    protected UUID nextId() {
        lastId = UUID.randomUUID();
        return lastId;
    }

    @Override
    public Product save(Product entity) {
        // todo implement
        throw new UnsupportedOperationException();
    }
}
