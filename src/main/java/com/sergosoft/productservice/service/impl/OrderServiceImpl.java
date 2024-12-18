package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.dto.order.item.OrderItemCreateDto;
import com.sergosoft.productservice.repository.OrderRepository;
import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import com.sergosoft.productservice.repository.entity.ProductEntity;
import com.sergosoft.productservice.service.OrderItemService;
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
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderItemService orderItemService;

    @Override
    @Transactional(readOnly = true)
    public OrderDetails getOrderById(UUID id) {
        return orderMapper.toOrderDetails(retrieveOrderOrElseThrow(id));
    }

    @Override
    @Transactional
    public OrderDetails createOrder(OrderCreateDto orderCreateDto) {
        log.debug("Creating order {}", orderCreateDto);
        OrderEntity orderToSave = OrderEntity.builder()
                .sellerReference(orderCreateDto.getSellerReference())
                .buyerReference(orderCreateDto.getBuyerReference())
                .build();

        // Form and attach the list of order items
        List<OrderItemEntity> orderItems = buildOrderItems(orderCreateDto.getItems(), orderToSave);
        orderToSave.setItems(orderItems);

        // Calculate and set the total price
        orderToSave.setTotalPrice(calculateTotalPrice(orderItems));

        // Save the order to the database
        OrderEntity savedOrder = saveOrderOrElseThrow(orderToSave);
        OrderDetails orderDetails = orderMapper.toOrderDetails(savedOrder);
        log.info("Created order details {}", orderDetails);
        return orderDetails;
    }

    @Override
    @Transactional
    public OrderDetails updateOrder(UUID id, OrderCreateDto orderUpdateDto) {
        log.debug("Updating order with id {} using {}", id, orderUpdateDto);

        // Retrieve the existing order from the database
        OrderEntity orderToUpdate = retrieveOrderOrElseThrow(id);

        // Update the main details of the order
        orderToUpdate = orderToUpdate.toBuilder()
                .sellerReference(orderUpdateDto.getSellerReference())
                .buyerReference(orderUpdateDto.getBuyerReference())
                .build();

        // Handle order items if the update DTO contains them
        if (orderUpdateDto.getItems() != null) {
            List<OrderItemEntity> currentItems = orderToUpdate.getItems();

            // Map the new items from the update DTO by their product IDs and merge quantities
            Map<UUID, OrderItemCreateDto> newItemsMap = orderUpdateDto.getItems().stream()
                    .collect(Collectors.toMap(
                            OrderItemCreateDto::getProductId,
                            Function.identity(),
                            (existing, replacement) -> {
                                // Merge quantities if the product IDs are the same
                                int mergedQuantity = existing.getQuantity() + replacement.getQuantity();
                                return OrderItemCreateDto.builder()
                                        .productId(replacement.getProductId())
                                        .quantity(mergedQuantity)
                                        .build();
                            }
                    ));

            // Identify items that need to be removed
            List<OrderItemEntity> itemsToRemove = currentItems.stream()
                    .filter(item -> !newItemsMap.containsKey(item.getProduct().getId()))
                    .toList();

            // Perform removal of outdated items
            itemsToRemove.forEach(orderItemService::delete);

            // Update existing items or create new ones
            List<OrderItemEntity> updatedItems = currentItems.stream()
                    .filter(item -> newItemsMap.containsKey(item.getProduct().getId()))
                    .peek(item -> {
                        OrderItemCreateDto dto = newItemsMap.get(item.getProduct().getId());
                        item.setQuantity(dto.getQuantity());
                        item.setPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
                    })
                    .toList();

            // Create new items
            OrderEntity finalOrderToUpdate = orderToUpdate;
            List<OrderItemEntity> newItems = newItemsMap.values().stream()
                    .filter(dto -> currentItems.stream()
                            .noneMatch(item -> item.getProduct().getId().equals(dto.getProductId())))
                    .map(dto -> {
                        ProductEntity product = productRepository.findById(dto.getProductId())
                                .orElseThrow(() -> new ProductNotFoundException(dto.getProductId().toString()));
                        return OrderItemEntity.builder()
                                .order(finalOrderToUpdate)
                                .product(product)
                                .quantity(dto.getQuantity())
                                .price(product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())))
                                .build();
                    })
                    .toList();

            // Combine updated and newly created items into a single list
            List<OrderItemEntity> finalItemList = Stream.concat(updatedItems.stream(), newItems.stream()).toList();

            // Update the order entity with the new list of items
            orderToUpdate.setItems(finalItemList);

            // Recalculate the total price of the order
            BigDecimal newTotalPrice = calculateTotalPrice(finalItemList);
            orderToUpdate.setTotalPrice(newTotalPrice);
        }

        // Save the updated order back to the database
        OrderEntity updatedOrder = saveOrderOrElseThrow(orderToUpdate);

        log.info("Updated order with id {}", updatedOrder.getId());

        // Convert the updated order entity into a response DTO and return it
        return orderMapper.toOrderDetails(updatedOrder);
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

    /**
     * Builds a list of order items from the provided DTOs and attaches them to the given order.
     */
    private List<OrderItemEntity> buildOrderItems(List<OrderItemCreateDto> itemDtos, OrderEntity order) {
        return itemDtos.stream()
                .map(itemDto -> {
                    ProductEntity product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(itemDto.getProductId().toString()));
                    BigDecimal itemTotalPrice = product.getPrice().multiply(new BigDecimal(itemDto.getQuantity()));
                    return OrderItemEntity.builder()
                            .order(order)
                            .product(product)
                            .quantity(itemDto.getQuantity())
                            .price(itemTotalPrice)
                            .build();
                })
                .toList();
    }

    /**
     * Calculates the total price for a list of order items.
     */
    private BigDecimal calculateTotalPrice(List<OrderItemEntity> orderItems) {
        return orderItems.stream()
                .map(OrderItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
