package com.sergosoft.productservice.web;

import com.sergosoft.productservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{customerId}/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // todo
}
