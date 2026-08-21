package com.example.leadservice.client;

import com.example.leadservice.dto.OpportunityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "OPPORTUNITY-SERVICE")
public interface OpportunityClient {

    @PostMapping("/api/opportunities")
    void createOpportunity(@RequestBody OpportunityRequest request);
}