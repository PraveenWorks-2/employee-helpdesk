package com.example.opportunityservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.opportunityservice.dto.OpportunityRequest;
import com.example.opportunityservice.dto.OpportunityResponse;
import com.example.opportunityservice.dto.StageUpdateRequest;
import com.example.opportunityservice.entity.Opportunity;
import com.example.opportunityservice.entity.OpportunityStage;
import com.example.opportunityservice.repository.OpportunityRepository;
import com.example.opportunityservice.service.OpportunityService;

@ExtendWith(MockitoExtension.class)
class OpportunityServiceTest {

    @Mock
    private OpportunityRepository opportunityRepository;

    @InjectMocks
    private OpportunityService opportunityService;

    @Test
    void createOpportunity_ShouldCreateOpportunity() {

        // Arrange
        OpportunityRequest request = new OpportunityRequest();
        request.setLeadId(1L);
        request.setName("Enterprise Deal");
        request.setDescription("CRM Subscription");
        request.setAmount(BigDecimal.valueOf(50000));

        Opportunity savedOpportunity = new Opportunity();
        savedOpportunity.setId(1L);
        savedOpportunity.setLeadId(1L);
        savedOpportunity.setName("Enterprise Deal");
        savedOpportunity.setDescription("CRM Subscription");
        savedOpportunity.setAmount(BigDecimal.valueOf(50000));
        savedOpportunity.setStage(OpportunityStage.NEW);
        savedOpportunity.setCreatedAt(LocalDateTime.now());

        when(opportunityRepository.save(any(Opportunity.class)))
                .thenReturn(savedOpportunity);

        // Act
        OpportunityResponse response =
                opportunityService.createOpportunity(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Enterprise Deal", response.getName());
        assertEquals(OpportunityStage.NEW, response.getStage());
    }

    @Test
    void updateStage_ShouldUpdateOpportunityStage() {

        // Arrange
        Opportunity opportunity = new Opportunity();
        opportunity.setId(1L);
        opportunity.setLeadId(1L);
        opportunity.setName("Enterprise Deal");
        opportunity.setStage(OpportunityStage.NEW);

        StageUpdateRequest request = new StageUpdateRequest();
        request.setStage(OpportunityStage.NEGOTIATION);

        when(opportunityRepository.findById(1L))
                .thenReturn(Optional.of(opportunity));

        when(opportunityRepository.save(any(Opportunity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OpportunityResponse response =
                opportunityService.updateStage(1L, request);

        // Assert
        assertEquals(OpportunityStage.NEGOTIATION, response.getStage());
    }
}