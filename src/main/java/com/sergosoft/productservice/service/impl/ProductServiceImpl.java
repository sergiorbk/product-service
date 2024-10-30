package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.repository.ProductRepository;
import com.sergosoft.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // todo
}
