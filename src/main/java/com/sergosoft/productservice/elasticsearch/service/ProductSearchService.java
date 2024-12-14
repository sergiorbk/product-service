package com.sergosoft.productservice.elasticsearch.service;

import com.sergosoft.productservice.domain.product.ProductDetails;
import com.sergosoft.productservice.elasticsearch.document.ProductSearchDocument;
import java.util.List;
import java.util.UUID;

public interface ProductSearchService {

    List<ProductDetails> findProductsByQuery(String query);

    ProductSearchDocument createProductDocument(ProductSearchDocument productSearchDocument);

    void deleteProductDocumentById(UUID id);

}
