package com.sergosoft.productservice.elasticsearch.repository;

import com.sergosoft.productservice.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<Product, UUID> {

}
