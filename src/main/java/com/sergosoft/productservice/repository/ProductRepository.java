package com.sergosoft.productservice.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import com.sergosoft.productservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
