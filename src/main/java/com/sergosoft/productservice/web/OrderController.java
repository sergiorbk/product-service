package com.sergosoft.productservice.web;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderCreateDto;
import com.sergosoft.productservice.dto.order.OrderResponseDto;
import com.sergosoft.productservice.service.impl.OrderServiceImpl;
import com.sergosoft.productservice.service.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id) {
        OrderDetails orderDetails = orderService.getOrderById(id);
        OrderResponseDto orderResponseDto = orderMapper.toOrderResponseDto(orderDetails);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        OrderDetails savedOrderDetails = orderService.createOrder(orderCreateDto);
        OrderResponseDto orderResponseDto = orderMapper.toOrderResponseDto(savedOrderDetails);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(orderResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody OrderCreateDto orderCreateDto) {
        OrderDetails updatedOrderDetails = orderService.updateOrder(id, orderCreateDto);
        OrderResponseDto orderResponseDto = orderMapper.toOrderResponseDto(updatedOrderDetails);
        return ResponseEntity.ok(orderResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
