package com.sergosoft.productservice.service.impl;

import java.util.Collection;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import com.sergosoft.productservice.service.CustomerService;
import com.sergosoft.productservice.service.mapper.OrderItemsMapper;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.dto.order.OrderCreationDto;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.service.exception.OrderNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderMapper orderMapper;
    private final OrderItemsMapper orderItemsMapper;

    private BigDecimal calculateTotalPrice(Collection<OrderItemEntity> items) {
        return items.stream()
                .map(OrderItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrderById(Long id) {
        log.info("Retrieving order by ID: {}", id);
        OrderEntity orderEntity = orderRepository.findById(id).orElse(null);
        log.info("Retrieved order entity: {}", orderEntity);
        return orderMapper.toOrder(orderEntity);
    }

    @Override
    public Order createOrder(OrderCreationDto orderCreationDto) {
        log.info("Creating new order: {}", orderCreationDto);

        OrderEntity orderToSave = new OrderEntity();
        orderToSave.setSeller(customerService.getCustomerById(orderCreationDto.getSellerId()));
        orderToSave.setBuyer(customerService.getCustomerById(orderCreationDto.getBuyerId()));
        orderToSave.setCreatedAt(Instant.now());

        orderToSave.setItems(orderCreationDto.getItems().stream().map(orderItemsMapper::toEntity).collect(Collectors.toSet()));
        orderToSave.setTotalPrice(calculateTotalPrice(orderToSave.getItems()));

        OrderEntity savedOrder = orderRepository.save(orderToSave);
        log.info("New order created successfully: {}", savedOrder);
        return orderMapper.toOrder(savedOrder);
    }

    @Override
    public Order updateOrder(Long id, OrderCreationDto orderCreationDto) {
        log.info("Updating order with ID: {}", id);
        OrderEntity existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        existingOrder.setBuyer(customerService.getCustomerById(orderCreationDto.getBuyerId()));
        existingOrder.setSeller(customerService.getCustomerById(orderCreationDto.getSellerId()));
        existingOrder.setItems(orderCreationDto.getItems().stream().map(orderItemsMapper::toEntity).collect(Collectors.toSet()));
        existingOrder.setTotalPrice(calculateTotalPrice(existingOrder.getItems()));

        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        log.info("Order updated successfully: {}", updatedOrder);
        return orderMapper.toOrder(updatedOrder);
    }

    @Override
    public void deleteOrderById(Long id) {
        log.info("Attempting to delete order with ID: {}", id);
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            log.info("Order with ID {} was deleted successfully.", id);
        } else {
            log.error("Unable to delete non-existent order with ID: {}", id);
            throw new OrderNotFoundException(id);
        }
    }
}
