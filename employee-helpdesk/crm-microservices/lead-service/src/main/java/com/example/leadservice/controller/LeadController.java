package com.example.leadservice.controller;

import com.example.leadservice.dto.AssignLeadRequest;
import com.example.leadservice.dto.LeadRequest;
import com.example.leadservice.dto.LeadResponse;
import com.example.leadservice.dto.QualifyLeadRequest;
import com.example.leadservice.service.LeadService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    // Constructor
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<LeadResponse> createLead(
            @Valid @RequestBody LeadRequest request) {

        LeadResponse response = leadService.createLead(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<LeadResponse>> getAllLeads() {

        List<LeadResponse> leads = leadService.getAllLeads();

        return ResponseEntity.ok(leads);
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<LeadResponse> assignLead(
            @PathVariable Long id,
            @Valid @RequestBody AssignLeadRequest request) {

        LeadResponse response =
                leadService.assignLead(id, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/qualify")
    public ResponseEntity<LeadResponse> qualifyLead(
            @PathVariable Long id,
            @Valid @RequestBody QualifyLeadRequest request) {

        LeadResponse response =
                leadService.qualifyLead(id, request);

        return ResponseEntity.ok(response);
    }
}