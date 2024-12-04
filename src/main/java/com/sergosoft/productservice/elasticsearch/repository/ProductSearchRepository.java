package com.sergosoft.productservice.elasticsearch.repository;

import com.sergosoft.productservice.elasticsearch.document.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSearchRepository
//        extends ElasticsearchRepository<ProductSearchDocument, UUID>
{

}
