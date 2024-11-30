package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.repository.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID> {

}
