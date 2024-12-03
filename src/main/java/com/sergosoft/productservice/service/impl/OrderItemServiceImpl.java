package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.repository.OrderItemRepository;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import com.sergosoft.productservice.service.OrderItemService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void delete(OrderItemEntity orderItemEntity) {
        log.debug("Deleting order item with id: {}", orderItemEntity.getId());
        try {
            orderItemRepository.delete(orderItemEntity);
            log.info("Deleted order item with id: {}", orderItemEntity.getId());
        } catch (Exception ex) {
            log.error("Exception occurred while deleting order item: {}", ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Transactional
    public void deleteOrderItems(OrderEntity order) {
        List<String> orderItemsIds = order.getItems().stream().map(OrderItemEntity::getId).map(UUID::toString).toList();
        log.debug("Delete order items with id: {}", orderItemsIds);
        try {
            orderItemRepository.deleteAllByOrder(order);
            log.info("Deleted order items: {}", orderItemsIds);
        } catch (Exception e) {
            log.error("Exception occurred while deleting order items of the defined order with id {}: {}", order.getId(), e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
