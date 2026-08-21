package com.example.opportunityservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.opportunityservice.dto.OpportunityRequest;
import com.example.opportunityservice.dto.OpportunityResponse;
import com.example.opportunityservice.dto.StageUpdateRequest;
import com.example.opportunityservice.service.OpportunityService;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;

    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    // CREATE OPPORTUNITY
    @PostMapping
    public ResponseEntity<OpportunityResponse> createOpportunity(
            @Valid @RequestBody OpportunityRequest request) {

        OpportunityResponse response =
                opportunityService.createOpportunity(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL OPPORTUNITIES
    @GetMapping
    public ResponseEntity<List<OpportunityResponse>> getAllOpportunities() {

        List<OpportunityResponse> opportunities =
                opportunityService.getAllOpportunities();

        return ResponseEntity.ok(opportunities);
    }

    // GET OPPORTUNITY BY ID
    @GetMapping("/{id}")
    public ResponseEntity<OpportunityResponse> getOpportunityById(
            @PathVariable("id") Long id) {

        OpportunityResponse response =
                opportunityService.getOpportunityById(id);

        return ResponseEntity.ok(response);
    }

    // UPDATE OPPORTUNITY STAGE
    @PutMapping("/{id}/stage")
    public ResponseEntity<OpportunityResponse> updateStage(
            @PathVariable("id") Long id,
            @Valid @RequestBody StageUpdateRequest request) {

        OpportunityResponse response =
                opportunityService.updateStage(id, request);

        return ResponseEntity.ok(response);
    }
 // DELETE OPPORTUNITY
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOpportunity(@PathVariable Long id) {

        opportunityService.deleteOpportunity(id);

        return ResponseEntity.ok("Opportunity deleted successfully.");
    }
}