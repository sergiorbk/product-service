package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreationDto;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public OrderDetails getOrderById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public OrderDetails createOrder(OrderCreationDto dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public OrderDetails updateOrder(Long id, OrderCreationDto dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
