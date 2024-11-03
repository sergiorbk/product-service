package com.sergosoft.productservice.service;

import com.sergosoft.productservice.config.MappersTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.sergosoft.productservice.service.impl.OrderServiceImpl;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = {OrderServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Order Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceTests {
    // todo
}
