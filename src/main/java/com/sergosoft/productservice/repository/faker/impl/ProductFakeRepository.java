package com.sergosoft.productservice.repository.faker.impl;

import java.util.Optional;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.repository.faker.FakeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * Fake repository of Product. Simulates CRUD operations on Product domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 */
@Repository
@Profile("fakeRepository")
//@Deprecated(forRemoval = true)
public class ProductFakeRepository extends FakeRepository<Product, Long> implements ProductRepository {

    @Override
    protected Long nextId() {
        lastId = lastId + 1;
        return lastId;
    }

    @Override
    public Product save(Product entity) {
        // todo implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Product> findById(Long primaryKey) {
        // todo implement
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        // todo implement
        throw new UnsupportedOperationException();
    }
}
