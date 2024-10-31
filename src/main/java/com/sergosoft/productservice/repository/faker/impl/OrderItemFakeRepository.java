package com.sergosoft.productservice.repository.faker.impl;

import org.springframework.stereotype.Repository;

import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.repository.OrderItemRepository;
import com.sergosoft.productservice.repository.faker.FakeRepository;

@Repository
public class OrderItemFakeRepository extends FakeRepository<OrderItem, Long> implements OrderItemRepository {

    @Override
    protected Long nextId() {
        if(lastId == null) {
            lastId = 0L;
        }
        lastId = lastId + 1;
        return lastId;
    }

    @Override
    public OrderItem save(OrderItem entity) {
        Long id = entity.getId() == null ? nextId() : entity.getId();
        database.put(id, new OrderItem(id, entity.getOrder(), entity.getProduct(), entity.getQuantity(), entity.getPrice()));
        return database.get(id);
    }
}
