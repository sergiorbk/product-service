package com.sergosoft.productservice.service;

import com.sergosoft.productservice.config.MappersTestConfiguration;
import com.sergosoft.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = {ProductServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Product Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTests {
    // todo Implement after connect JPA Repos
}
