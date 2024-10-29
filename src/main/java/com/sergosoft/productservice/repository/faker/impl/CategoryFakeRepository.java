package com.sergosoft.productservice.repository.faker.impl;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.repository.CategoryRepository;
import com.sergosoft.productservice.repository.faker.FakeRepository;

/**
 * Fake repository of Category. Simulates CRUD operations on Category domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 */
@Repository
@Profile("fakeRepository")
//@Deprecated(forRemoval = true)
public class CategoryFakeRepository extends FakeRepository<Category, Integer> implements CategoryRepository {

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
