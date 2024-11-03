package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.repository.faker.FakeCrudRepository;

import java.util.List;

public interface OrderItemRepository extends FakeCrudRepository<OrderItem, Long> {
    void deleteAll(List<OrderItem> items);
    List<OrderItem> findByOrderId(Long orderId);
    // todo implement as JPA repository
}
