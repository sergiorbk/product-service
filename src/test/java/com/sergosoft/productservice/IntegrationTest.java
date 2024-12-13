package com.sergosoft.productservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public abstract class IntegrationTest {

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).configureStaticDsl(true).build();

    @DynamicPropertySource
    static void setupTestContainerProperties(DynamicPropertyRegistry registry) {

        registry.add("application.payment-service.base-path", wireMockServer::baseUrl);
        WireMock.configureFor(wireMockServer.getPort());
    }
}
