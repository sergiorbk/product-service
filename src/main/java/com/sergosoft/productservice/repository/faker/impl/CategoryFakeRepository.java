package com.sergosoft.productservice.repository.faker.impl;

import java.util.Optional;

import com.sergosoft.productservice.repository.CategoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.sergosoft.productservice.domain.Category;
import com.sergosoft.productservice.repository.faker.FakeRepository;

/**
 * Fake repository of Category. Simulates CRUD operations on Category domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 */
@Repository
@Profile("fakeRepository")
//@Deprecated(forRemoval = true)
public class CategoryFakeRepository extends FakeRepository<Category, Integer> implements CategoryRepository {

    public CategoryFakeRepository() {
        lastId = 0;
    }

    @Override
    protected void nextId() {
        lastId = lastId + 1;
    }

    @Override
    public Category save(Category entity) {
        nextId();
        return database.put(lastId, new Category(lastId, entity.getTitle(), entity.getParent()));
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
