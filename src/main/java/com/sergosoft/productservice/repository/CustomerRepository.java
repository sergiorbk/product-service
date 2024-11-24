package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.repository.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends NaturalIdRepository<CustomerEntity, UUID> {

}
