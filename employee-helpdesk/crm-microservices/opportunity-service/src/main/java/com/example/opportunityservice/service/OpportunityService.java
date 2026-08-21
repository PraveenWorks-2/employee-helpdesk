package com.example.opportunityservice.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.opportunityservice.dto.OpportunityRequest;
import com.example.opportunityservice.dto.OpportunityResponse;
import com.example.opportunityservice.dto.StageUpdateRequest;
import com.example.opportunityservice.entity.Opportunity;
import com.example.opportunityservice.entity.OpportunityStage;
import com.example.opportunityservice.exception.OpportunityNotFoundException;
import com.example.opportunityservice.repository.OpportunityRepository;

@Service
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;

    public OpportunityService(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    // CREATE OPPORTUNITY
    public OpportunityResponse createOpportunity(
            OpportunityRequest request) {

        Opportunity opportunity = new Opportunity();

        opportunity.setLeadId(request.getLeadId());
        opportunity.setName(request.getName());
        opportunity.setDescription(request.getDescription());
        opportunity.setAmount(request.getAmount());
        opportunity.setStage(OpportunityStage.NEW);
        opportunity.setCreatedAt(LocalDateTime.now());

        Opportunity savedOpportunity =
                opportunityRepository.save(opportunity);

        return mapToResponse(savedOpportunity);
    }

    // UPDATE OPPORTUNITY STAGE
    public OpportunityResponse updateStage(
            Long id,
            StageUpdateRequest request) {

        Opportunity opportunity =
                opportunityRepository.findById(id)
                        .orElseThrow(() ->
                                new OpportunityNotFoundException(
                                        "Opportunity not found with id: " + id
                                ));

        opportunity.setStage(request.getStage());

        Opportunity updatedOpportunity =
                opportunityRepository.save(opportunity);

        return mapToResponse(updatedOpportunity);
    }

    // ENTITY TO RESPONSE DTO
    private OpportunityResponse mapToResponse(
            Opportunity opportunity) {

        OpportunityResponse response =
                new OpportunityResponse();

        response.setId(opportunity.getId());
        response.setLeadId(opportunity.getLeadId());
        response.setName(opportunity.getName());
        response.setDescription(opportunity.getDescription());
        response.setAmount(opportunity.getAmount());
        response.setStage(opportunity.getStage());
        response.setCreatedAt(opportunity.getCreatedAt());

        return response;
    }
 // GET ALL OPPORTUNITIES
    public List<OpportunityResponse> getAllOpportunities() {

        return opportunityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
 // GET OPPORTUNITY BY ID
    public OpportunityResponse getOpportunityById(Long id) {

        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() ->
                        new OpportunityNotFoundException(
                                "Opportunity not found with id: " + id
                        ));

        return mapToResponse(opportunity);
    }
 // DELETE OPPORTUNITY
    public void deleteOpportunity(Long id) {

        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() ->
                        new OpportunityNotFoundException(
                                "Opportunity not found with id: " + id
                        ));

        opportunityRepository.delete(opportunity);
    }
}