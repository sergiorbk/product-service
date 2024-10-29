package com.sergosoft.productservice.repository.faker.impl;

import java.util.Optional;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.repository.faker.FakeRepository;
import org.springframework.stereotype.Repository;

/**
 * Fake repository of Category. Simulates CRUD operations on Category domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 */
@Repository
//@Deprecated(forRemoval = true)
public class CategoryFakeRepository extends FakeRepository<Category, Integer> {

    @Override
    protected Integer nextId() {
        lastId = lastId + 1;
        return lastId;
    }

    @Override
    public Category save(Category entity) {
        return database.put(nextId(), entity);
    }

    @Override
    public Optional<Category> findById(Integer primaryKey) {
        return Optional.ofNullable(database.get(primaryKey));
    }

    @Override
    public void delete(Integer id) {
        database.remove(id);
    }
}
