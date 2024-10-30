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

    @Override
    protected void nextId() {
        if(lastId == null) {
            lastId = 0;
        }
        lastId = lastId + 1;
    }

    @Override
    public Category save(Category entity) {
        nextId();
        database.put(lastId, Category.builder()
                .id(lastId)
                .title(entity.getTitle())
                .parent(entity.getParent())
                .build()
        );
        return database.get(lastId);
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
