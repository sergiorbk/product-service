package com.sergosoft.productservice.web;

import java.util.UUID;

import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.dto.order.OrderResponseDto;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.service.impl.OrderServiceImpl;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        OrderEntity savedOrder = orderService.createOrder(orderCreateDto);
        OrderResponseDto orderResponseDto = orderMapper.toOrderResponseDto(savedOrder);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable UUID id) {
        OrderEntity order = orderService.findOrderById(id);
        OrderResponseDto orderResponseDto = orderMapper.toOrderResponseDto(order);
        return ResponseEntity.ok(orderResponseDto);
    }

}
