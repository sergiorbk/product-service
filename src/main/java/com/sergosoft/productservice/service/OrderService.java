package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreateDto;

import java.util.UUID;

public interface OrderService {

    OrderDetails getOrderById(UUID id);
    OrderDetails createOrder(OrderCreateDto dto);
    OrderDetails updateOrder(UUID id, OrderCreateDto dto);
    void deleteOrderById(UUID id);

}
