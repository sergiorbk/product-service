package com.sergosoft.productservice.repository.faker.impl;

import com.sergosoft.productservice.repository.CategoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.sergosoft.productservice.repository.faker.FakeRepository;
import com.sergosoft.productservice.domain.Category;

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
        database.put(id, Category.builder()
                .id(id)
                .title(entity.getTitle())
                .parent(entity.getParent())
                .build()
        );
        return database.get(id);
    }
}
