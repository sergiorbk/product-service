package com.sergosoft.productservice.service;

import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.dto.order.item.OrderItemCreationDto;

import java.util.List;

public interface OrderItemService {

    OrderItem getOrderItemById(Long id);
    OrderItem createOrderItem(Order order, OrderItemCreationDto dto);
    List<OrderItem> createOrderItems(Order order, List<OrderItemCreationDto> dtoList);
    OrderItem updateOrderItem(Long id, OrderItemCreationDto dto);
    void deleteAllByOrderId(Long id);
}
