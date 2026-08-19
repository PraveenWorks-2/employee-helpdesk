package com.example.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {

        return builder.routes()

                .route("purchase-service", r -> r
                        .path("/api/purchases/**")
                        .uri("lb://PURCHASE-SERVICE"))

                .route("approval-service", r -> r
                        .path("/api/approvals/**")
                        .uri("lb://APPROVAL-SERVICE"))

                .build();
    }
}