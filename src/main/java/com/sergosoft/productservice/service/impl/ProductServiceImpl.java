package com.sergosoft.productservice.service.impl;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.sergosoft.productservice.service.ProductService;
import com.sergosoft.productservice.repository.ProductRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // todo
}
