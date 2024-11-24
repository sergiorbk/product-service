package com.sergosoft.productservice.service.impl;

import com.sergosoft.productservice.repository.CustomerRepository;
import com.sergosoft.productservice.repository.entity.CustomerEntity;
import com.sergosoft.productservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerEntity getCustomerById(UUID id) {
        log.info("Retrieving the customer with id {}", id);
        CustomerEntity retrievedCustomer = customerRepository.getReferenceById(id);
        log.info("Retrieved customer with id {}", retrievedCustomer.getId());
        return retrievedCustomer;
    }

}
