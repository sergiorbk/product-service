package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.service.OrderService;
import com.sergosoft.productservice.service.exception.order.OrderNotFoundException;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import jakarta.persistence.PersistenceException;
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
    public OrderDetails createOrder(OrderCreateDto dto) {
        log.info("Creating order: {}", dto);
        OrderEntity orderToCreate = orderMapper.toOrderEntity(dto);
        // add or change attributes via the builder
        orderToCreate = orderToCreate.toBuilder()
                //todo
//                .items()
//                .sellerReference()
//                .buyerReference()
//                .totalPrice
                .build();
        // save the product
        OrderEntity savedOrder = saveOrderOrElseThrow(orderToCreate);
        // return saved product details
        return orderMapper.toOrderDetails(savedOrder);
    }

    @Override
    @Transactional
    public OrderDetails updateOrder(UUID id, OrderCreateDto dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID id) {
        log.debug("Deleting order with id: {}", id);
        try{
            orderRepository.deleteById(id);
            log.info("Deleted order with id: {}", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting order with id {}: {}", id, ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
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

    @Transactional
    public OrderEntity saveOrderOrElseThrow(OrderEntity orderToSave) {
        log.debug("Saving order: {}", orderToSave);
        try{
            OrderEntity savedProduct = orderRepository.save(orderToSave);
            log.info("Saved order: {}", savedProduct);
            return savedProduct;
        } catch (Exception ex) {
            log.error("Exception occurred while saving order: {}", orderToSave);
            throw new PersistenceException(ex.getMessage());
        }
    }
}
