package com.sergosoft.productservice.repository.faker.impl;

import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;

import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.repository.faker.FakeRepository;

/**
 * Fake repository of Order. Simulates CRUD operations on Order domains.<br>
 * Created for demo and test purposes only and will be removed after implementing JPA repositories.
 */
@Repository
@Profile("fakeRepository")
//@Deprecated(forRemoval = true)
public class OrderFakeRepository extends FakeRepository<Order, Long> implements OrderRepository {

    @Override
    protected Long nextId() {
        if(lastId == null) {
            lastId = 0L;
        }
        lastId = lastId + 1;
        return lastId;
    }

    @Override
    public Order save(Order entity) {
        Long id = entity.getId() == null ? nextId() : entity.getId();
        database.put(id, new Order(id, entity.getItems(), entity.getSellerId(), entity.getBuyerId(),
                entity.getTotalPrice(), entity.getCreatedAt()));
        return database.get(id);
    }
}
