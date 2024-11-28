package com.sergosoft.productservice.elasticsearch.repository;

import com.sergosoft.productservice.domain.product.ProductDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDetails, UUID> {

}
