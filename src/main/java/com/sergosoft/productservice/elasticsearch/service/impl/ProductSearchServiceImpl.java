package com.sergosoft.productservice.elasticsearch.service.impl;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.elasticsearch.repository.ProductSearchRepository;
import com.sergosoft.productservice.elasticsearch.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

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
}
