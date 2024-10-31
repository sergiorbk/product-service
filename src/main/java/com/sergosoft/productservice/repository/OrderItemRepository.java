package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.repository.faker.FakeCrudRepository;

public interface OrderItemRepository extends FakeCrudRepository<OrderItem, Long> {
    // todo implement as JPA repository
}
