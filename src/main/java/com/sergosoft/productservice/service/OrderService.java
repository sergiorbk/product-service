package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.dto.order.OrderCreationDto;

public interface OrderService {

    Order getOrderById(Long id);
    Order createOrder(OrderCreationDto dto);
    Order updateOrder(Long id, OrderCreationDto dto);
    void deleteOrderById(Long id);
}
