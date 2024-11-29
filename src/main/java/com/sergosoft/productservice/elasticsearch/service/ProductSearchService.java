package com.sergosoft.productservice.elasticsearch.service;

import com.sergosoft.productservice.domain.product.ProductDetails;
import java.util.List;

public interface ProductSearchService {

    List<ProductDetails> findProductsByQuery(String query);

}
