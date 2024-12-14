package com.sergosoft.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.annotation.Nonnull;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${DB_ELASTIC_URL}")
    private String HOST_AND_PORT;

    @Value("${DB_ELASTIC_CA_FINGERPRINT}")
    private String CA_FINGERPRINT;

    @Value("${DB_ELASTIC_USERNAME}")
    private String USERNAME;

    @Value("${DB_ELASTIC_PASSWORD}")
    private String PASSWORD;

    @Nonnull
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(HOST_AND_PORT)
                .usingSsl(CA_FINGERPRINT)
                .withBasicAuth(USERNAME, PASSWORD)
                .build();
    }
}
