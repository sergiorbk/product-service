package com.sergosoft.productservice.repository.faker.impl;

import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import com.sergosoft.productservice.repository.CategoryRepository;

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
    protected Integer nextId() {
        if(lastId == null) {
            lastId = 0;
        }
        lastId = lastId + 1;
        return lastId;
    }

    @Override
    public Category save(Category entity) {
        Integer id = entity.getId() == null ? nextId() : entity.getId();
        Category categoryToSave = new Category(id, entity.getTitle(), entity.getParent());
        database.put(id, categoryToSave);
        return database.get(id);
    }
}
