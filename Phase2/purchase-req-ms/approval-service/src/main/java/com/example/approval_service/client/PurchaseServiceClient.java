package com.example.approval_service.client;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PurchaseServiceClient {

    private final RestClient purchaseRestClient;
    private final HttpServletRequest httpServletRequest;

    public PurchaseServiceClient(
            RestClient purchaseRestClient,
            HttpServletRequest httpServletRequest) {

        this.purchaseRestClient = purchaseRestClient;
        this.httpServletRequest = httpServletRequest;
    }

    public void updatePurchaseStatus(
            Long purchaseId,
            String status) {

        String authorization =
                httpServletRequest.getHeader(
                        HttpHeaders.AUTHORIZATION);

        purchaseRestClient
                .patch()
                .uri(
                        "/api/purchases/{id}/status",
                        purchaseId
                )
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        authorization
                )
                .body("""
                        {
                            "status": "%s"
                        }
                        """.formatted(status))
                .retrieve()
                .toBodilessEntity();
    }
}