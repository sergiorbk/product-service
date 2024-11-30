package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreationDto;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.service.exception.order.OrderNotFoundException;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public OrderDetails getOrderById(UUID id) {
        return orderMapper.toOrderDetails(retrieveOrderEntityOrElseThrow(id));
    }

    @Override
    @Transactional
    public OrderDetails createOrder(OrderCreationDto dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public OrderDetails updateOrder(UUID id, OrderCreationDto dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly = true)
    public OrderEntity retrieveOrderEntityOrElseThrow(UUID id) {
        log.debug("Retrieving order by id: {}: ", id);
        OrderEntity retrievedOrder = orderRepository.findById(id).orElseThrow(() -> {
            log.error("Exception occurred while retrieving order with id {}:", id);
            return new OrderNotFoundException(id);
        });
        log.info("Retrieved order entity with id {}: {}", id, retrievedOrder);
        return retrievedOrder;
    }
}
