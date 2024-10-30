package com.sergosoft.productservice.web;

import com.sergosoft.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // todo
}
