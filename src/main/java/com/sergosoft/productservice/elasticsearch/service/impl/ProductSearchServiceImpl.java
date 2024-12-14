package com.sergosoft.productservice.elasticsearch.service.impl;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.elasticsearch.document.ProductSearchDocument;
import com.sergosoft.productservice.elasticsearch.repository.ProductSearchRepository;
import com.sergosoft.productservice.elasticsearch.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    @Override
    public List<ProductDetails> findProductsByQuery(String query) {
        // todo implement
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProductSearchDocument createProductDocument(ProductSearchDocument productSearchDocument) {
        log.debug("Creating product search document: {}", productSearchDocument);
        ProductSearchDocument savedProductSearchDocument = saveProductToOrElseThrow(productSearchDocument);
        log.info("Saved product search document: {}", savedProductSearchDocument);
        return savedProductSearchDocument;
    }

    @Override
    public void deleteProductDocumentById(UUID id) {
        log.debug("Deleting product search document with id: {}", id);
        try {
            productSearchRepository.deleteById(id);
            log.info("Deleted product search document with id: {}", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting product search document with id: {}", id, ex);
            throw new ElasticsearchException(ex.getMessage());
        }
    }

    public ProductSearchDocument saveProductToOrElseThrow(ProductSearchDocument productToSave) {
        log.debug("Saving product to Elasticsearch repository {}", productToSave);
        try {
            ProductSearchDocument savedProduct = productSearchRepository.save(productToSave);
            log.info("Saved product to Elasticsearch repository {}", savedProduct);
            return savedProduct;
        } catch (Exception ex) {
            log.error("Exception occurred while saving product to Elasticsearch repository {}", ex.getMessage());
            throw new ElasticsearchException(ex.getMessage());
        }
    }

}
