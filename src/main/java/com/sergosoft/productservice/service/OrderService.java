package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreationDto;

public interface OrderService {

    OrderDetails getOrderById(Long id);
    OrderDetails createOrder(OrderCreationDto dto);
    OrderDetails updateOrder(Long id, OrderCreationDto dto);
    void deleteOrderById(Long id);
}
