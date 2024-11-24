package com.sergosoft.productservice.service;

import com.sergosoft.productservice.repository.entity.CustomerEntity;

import java.util.UUID;

public interface CustomerService {

    CustomerEntity getCustomerById(UUID id);
}
