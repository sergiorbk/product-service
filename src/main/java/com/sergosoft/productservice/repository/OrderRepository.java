package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.repository.faker.FakeCrudRepository;

public interface OrderRepository extends FakeCrudRepository<Order, Long> {
    // todo implement as a JPA repository
}
