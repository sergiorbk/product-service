package com.sergosoft.productservice.service;

import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;

public interface OrderItemService {

    void delete(OrderItemEntity orderItemEntity);

    void deleteOrderItems(OrderEntity order);

}
