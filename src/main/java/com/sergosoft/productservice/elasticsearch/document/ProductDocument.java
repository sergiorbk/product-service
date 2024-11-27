package com.sergosoft.productservice.elasticsearch.document;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "product")
public class ProductDocument {


}
