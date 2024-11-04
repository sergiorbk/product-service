package com.sergosoft.productservice.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.sergosoft.productservice.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);
}
