package com.sergosoft.productservice.repository.faker.impl;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.repository.faker.FakeRepository;

/**
 * Fake repository of Product. Simulates CRUD operations on Product domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 */
@Repository
@Profile("fakeRepository")
//@Deprecated(forRemoval = true)
public class ProductFakeRepository extends FakeRepository<Product, UUID> implements ProductRepository {

    @Override
    protected UUID nextId() {
        lastId = UUID.randomUUID();
        return lastId;
    }

    @Override
    public Product save(Product entity) {
       UUID id = entity.getId() == null ? nextId() : entity.getId();
        database.put(id, new Product(id, entity.getOwnerId(), entity.getTitle(), entity.getDescription(),
                entity.getCategories(), entity.getPrice())
        );
        return database.get(id);
    }
}
