package com.sergosoft.productservice.web;

import java.net.URI;
import java.util.UUID;

import com.sergosoft.productservice.service.mapper.OrderMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreationDto;
import com.sergosoft.productservice.dto.order.OrderResponseDto;
import com.sergosoft.productservice.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id) {
        OrderDetails retrievedOrderDetails = orderService.getOrderById(id);
        OrderResponseDto categoryResponseDto = orderMapper.toOrderResponseDto(retrievedOrderDetails);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderCreationDto dto) {
        OrderDetails createdOrderDetails = orderService.createOrder(dto);
        OrderResponseDto createdOrderResponseDto = orderMapper.toOrderResponseDto(createdOrderDetails);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrderResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdOrderResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable UUID id,
                                                        @RequestBody @Valid OrderCreationDto orderDto) {
        OrderDetails updatedOrderDetails = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(updatedOrderDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

}
