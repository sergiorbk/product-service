package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.repository.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends NaturalRepository<ProductEntity, UUID> {

}
