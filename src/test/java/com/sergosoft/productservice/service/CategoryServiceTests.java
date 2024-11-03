package com.sergosoft.productservice.service;

import com.sergosoft.productservice.config.MappersTestConfiguration;
import com.sergosoft.productservice.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = {CategoryServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Category Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceTests {
    // todo
}
