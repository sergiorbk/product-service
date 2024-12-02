package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import com.sergosoft.productservice.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderEntity createOrder(OrderCreateDto orderCreateDto) {
        BigDecimal totalPrice = orderCreateDto.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = OrderEntity.builder()
                .sellerReference(orderCreateDto.getSellerReference())
                .buyerReference(orderCreateDto.getBuyerReference())
                .totalPrice(totalPrice)
                .build();

        List<OrderItemEntity> orderItems = orderCreateDto.getItems().stream().map(itemDTO -> {
            ProductEntity product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId().toString()));

            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .price(itemDTO.getPrice())
                    .build();

            return orderItem;
        }).toList();

        order.setItems(orderItems);
        return orderRepository.save(order);
    }


    @Transactional(readOnly = true)
    public OrderEntity findOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
}
