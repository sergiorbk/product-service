package com.sergosoft.productservice.repository.faker.impl;

import java.util.Optional;

import com.sergosoft.productservice.domain.Order;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.repository.faker.FakeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

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
        lastId = lastId + 1;
        return lastId;
    }

    @Override
    public Order save(Order entity) {
        // todo implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Order> findById(Long primaryKey) {
        // todo implement
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        // todo implement
        throw new UnsupportedOperationException();
    }
}
