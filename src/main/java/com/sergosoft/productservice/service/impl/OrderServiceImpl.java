package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.service.exception.ProductNotFoundException;
import com.sergosoft.productservice.service.exception.order.OrderNotFoundException;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public OrderDetails getOrderById(UUID id) {
        return orderMapper.toOrderDetails(retrieveOrderOrElseThrow(id));
    }

    @Override
    @Transactional
    public OrderDetails createOrder(OrderCreateDto orderCreateDto) {
        log.debug("Creating order {}", orderCreateDto);
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

        OrderEntity orderEntity = saveOrderOrElseThrow(order);
        OrderDetails orderDetails = orderMapper.toOrderDetails(orderEntity);
        log.info("Created order details {}", orderDetails);
        return orderDetails;
    }

    @Override
    @Transactional
    public OrderDetails updateOrder(UUID id, OrderCreateDto dto) {
        log.debug("Updating order with id {} using {}", id, dto);

        // Retrieve the existing order
        OrderEntity existingOrder = retrieveOrderOrElseThrow(id);

        // Calculate the total price for the updated items
        BigDecimal updatedTotalPrice = dto.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Update the main order details
        existingOrder.setSellerReference(dto.getSellerReference());
        existingOrder.setBuyerReference(dto.getBuyerReference());
        existingOrder.setTotalPrice(updatedTotalPrice);

        // Clear and rebuild the order items list
        existingOrder.getItems().clear();

        List<OrderItemEntity> updatedItems = dto.getItems().stream().map(itemDTO -> {
            ProductEntity product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId().toString()));

            return OrderItemEntity.builder()
                    .order(existingOrder)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .price(itemDTO.getPrice())
                    .build();
        }).toList();

        existingOrder.getItems().addAll(updatedItems);

        // Save the updated order
        OrderEntity updatedOrder = saveOrderOrElseThrow(existingOrder);
        OrderDetails updatedOrderDetails = orderMapper.toOrderDetails(updatedOrder);

        log.info("Updated order details {}", updatedOrderDetails);
        return updatedOrderDetails;
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID id) {
        log.debug("Deleting order by id {}", id);
        try {
            orderRepository.deleteById(id);
            log.info("Deleted order by id {}", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting order by id {}", id, ex);
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public OrderEntity retrieveOrderOrElseThrow(UUID id) {
        log.debug("Retrieving order by id {}", id);
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        log.info("Retrieved order details {}", orderEntity);
        return orderEntity;
    }

    @Transactional
    public OrderEntity saveOrderOrElseThrow(OrderEntity orderEntity) {
        log.debug("Saving order {}", orderEntity);
        try {
            OrderEntity savedOrder = orderRepository.save(orderEntity);
            log.info("Saved order {}", orderEntity);
            return savedOrder;
        } catch (Exception ex) {
            log.error("Exception occurred while saving order {}", orderEntity, ex);
            throw new PersistenceException(ex.getMessage());
        }
    }
}
