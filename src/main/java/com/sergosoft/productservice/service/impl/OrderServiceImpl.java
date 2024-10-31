package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.service.OrderItemService;
import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.service.exception.OrderNotFoundException;
import com.sergosoft.productservice.dto.order.OrderCreationDto;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    @Override
    public Order getOrderById(Long id) {
        log.info("Retrieving order by ID: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public Order createOrder(OrderCreationDto orderCreationDto) {
        log.info("Creating new order: {}", orderCreationDto);

        Order orderToSave = Order.builder()
                .sellerId(orderCreationDto.getSellerId())
                .buyerId(orderCreationDto.getBuyerId())
                .createdAt(Instant.now())
                .items(List.of())
                .build();

        Order savedOrder = orderRepository.save(orderToSave);

        List<OrderItem> orderItems = orderItemService.createOrderItems(savedOrder, orderCreationDto.getItems());

        savedOrder = savedOrder.toBuilder().items(orderItems).build();
        savedOrder = orderRepository.save(savedOrder);

        log.info("New order created successfully: {}", savedOrder);
        return savedOrder;
    }

    @Override
    public Order updateOrder(Long id, OrderCreationDto orderCreationDto) {
        log.info("Updating order with ID: {}", id);
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        orderItemService.deleteAllByOrderId(id);

        List<OrderItem> updatedItems = orderItemService.createOrderItems(existingOrder, orderCreationDto.getItems());

        Order updatedOrder = existingOrder.toBuilder()
                .sellerId(orderCreationDto.getSellerId())
                .buyerId(orderCreationDto.getBuyerId())
                .items(updatedItems)
                .build();

        updatedOrder = orderRepository.save(updatedOrder);
        log.info("Order updated successfully: {}", updatedOrder);
        return updatedOrder;
    }

    @Override
    public void deleteOrderById(Long id) {
        log.info("Attempting to delete order with ID: {}", id);
        if (orderRepository.existsById(id)) {
            orderItemService.deleteAllByOrderId(id);
            orderRepository.deleteById(id);
            log.info("Order with ID {} was deleted successfully.", id);
        } else {
            log.error("Unable to delete non-existent order with ID: {}", id);
            throw new OrderNotFoundException(id);
        }
    }
}