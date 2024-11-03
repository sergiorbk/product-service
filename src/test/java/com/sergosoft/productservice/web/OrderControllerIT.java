package com.sergosoft.productservice.web;

import com.sergosoft.productservice.IntegrationTest;

import com.sergosoft.productservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;

import static org.mockito.Mockito.reset;

@AutoConfigureMockMvc
@DisplayName("Order Controller IT")
@Tag("order-service")
public class OrderControllerIT extends IntegrationTest {

    private final UUID SELLER_ID = UUID.randomUUID();
    private final UUID BUYER_ID = UUID.randomUUID();

    @SpyBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        reset(orderService);
    }

    // todo
}
