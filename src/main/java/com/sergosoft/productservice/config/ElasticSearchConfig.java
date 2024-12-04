package com.sergosoft.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl("986e9de87e3bb6a17e97271ffb983d120725bb80f1370dd2fa5e70ff63281163")
                .withBasicAuth("elastic", "pEh8gKLNbkFncUfoTz8b")
                .build();
    }
}