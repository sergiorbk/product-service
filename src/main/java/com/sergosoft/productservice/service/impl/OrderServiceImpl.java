package com.sergosoft.productservice.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.repository.OrderRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    // todo
}
