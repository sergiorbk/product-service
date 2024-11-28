package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.repository.entity.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, String> {

}
