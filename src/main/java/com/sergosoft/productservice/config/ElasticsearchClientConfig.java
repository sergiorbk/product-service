package com.sergosoft.productservice.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
@EnableElasticsearchRepositories(basePackages = "com.sergosoft.productservice.elasticsearch.repository")
@ComponentScan(basePackages = { "com.sergosoft.productservice.elasticsearch" })
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {

    private String host;
    private int port;

    @Override
    @Bean
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(String.format("%s:%s", host, port))
                .build();
    }

    @Bean
    public RestClient elasticsearchClient() {
        return RestClient.builder(new HttpHost(host, port, "http")).build();
    }
}
