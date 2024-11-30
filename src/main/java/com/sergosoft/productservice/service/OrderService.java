package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreationDto;

import java.util.UUID;

public interface OrderService {

    OrderDetails getOrderById(UUID id);
    OrderDetails createOrder(OrderCreationDto dto);
    OrderDetails updateOrder(UUID id, OrderCreationDto dto);
    void deleteOrderById(UUID id);
}
