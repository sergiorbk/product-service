package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    // todo
}
