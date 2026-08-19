package com.example.approval_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient purchaseRestClient(
            @Value("${purchase-service.url}") String purchaseServiceUrl) {

        return RestClient
                .builder()
                .baseUrl(purchaseServiceUrl)
                .build();
    }
}