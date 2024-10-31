package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.domain.Product;
import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.dto.order.item.OrderItemCreationDto;
import com.sergosoft.productservice.repository.OrderItemRepository;
import com.sergosoft.productservice.service.OrderItemService;
import com.sergosoft.productservice.service.ProductService;
import com.sergosoft.productservice.service.exception.OrderItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository itemRepository;
    private final ProductService productService;

    @Override
    public OrderItem getOrderItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
    }

    @Override
    public List<OrderItem> createOrderItems(Order order, List<OrderItemCreationDto> dtoList) {
        return dtoList.stream()
                .map(dto -> createOrderItem(order, dto))
                .toList();
    }

    @Override
    public OrderItem createOrderItem(Order order, OrderItemCreationDto dto) {
        Product product = productService.getProductById(dto.getProductId());
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(dto.getQuantity())
                .price(totalPrice)
                .build();

        return itemRepository.save(orderItem);
    }


    @Override
    public OrderItem updateOrderItem(Long id, OrderItemCreationDto dto) {
        OrderItem existingOrderItem = getOrderItemById(id);

        Product product = productService.getProductById(dto.getProductId());
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

        OrderItem updatedOrderItem = existingOrderItem.toBuilder()
                .product(product)
                .quantity(dto.getQuantity())
                .price(totalPrice)
                .build();

        return itemRepository.save(updatedOrderItem);
    }

    @Override
    public void deleteAllByOrderId(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new OrderItemNotFoundException(id);
        }
    }
}
