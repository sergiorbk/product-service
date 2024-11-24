package com.sergosoft.productservice.service.impl;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.service.ProductService;
import com.sergosoft.productservice.service.OrderItemService;
import com.sergosoft.productservice.repository.OrderItemRepository;
import com.sergosoft.productservice.dto.order.item.OrderItemCreationDto;
import com.sergosoft.productservice.service.exception.OrderItemNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository itemRepository;
    private final ProductService productService;

    @Override
    public OrderItem getOrderItemById(Long id) {
        log.info("Fetching order item with ID: {}", id);
        return itemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order item with ID: {} not found", id);
                    return new OrderItemNotFoundException(id);
                });
    }

    @Override
    public List<OrderItem> createOrderItems(Order order, List<OrderItemCreationDto> dtoList) {
        log.info("Creating order items for order ID: {}", order.getId());
        List<OrderItem> orderItems = dtoList.stream()
                .map(dto -> createOrderItem(order, dto))
                .toList();
        log.info("Created {} order items for order ID: {}", orderItems.size(), order.getId());
        return orderItems;
    }

    @Override
    public OrderItem createOrderItem(Order order, OrderItemCreationDto dto) {
        log.info("Creating order item for product ID: {} with quantity: {}", dto.getProductId(), dto.getQuantity());
        Product product = productService.getProductById(dto.getProductId());
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(dto.getQuantity())
                .price(totalPrice)
                .build();

        OrderItem savedOrderItem = itemRepository.save(orderItem);
        log.info("Order item created with ID: {} and total price: {}", savedOrderItem.getId(), totalPrice);
        return savedOrderItem;
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItemCreationDto dto) {
        log.info("Updating order item with ID: {}", id);
        OrderItem existingOrderItem = getOrderItemById(id);

        Product product = productService.getProductById(dto.getProductId());
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

        OrderItem updatedOrderItem = existingOrderItem.toBuilder()
                .product(product)
                .quantity(dto.getQuantity())
                .price(totalPrice)
                .build();

        OrderItem savedOrderItem = itemRepository.save(updatedOrderItem);
        log.info("Order item updated with ID: {} and new total price: {}", savedOrderItem.getId(), totalPrice);
        return savedOrderItem;
    }

    @Override
    public void deleteAllByOrderId(Long orderId) {
        log.info("Deleting all items for order ID: {}", orderId);
        itemRepository.deleteByOrderId(orderId);
    }
}
