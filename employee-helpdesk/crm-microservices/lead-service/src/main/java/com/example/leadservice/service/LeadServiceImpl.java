package com.example.leadservice.service;

import com.example.leadservice.dto.AssignLeadRequest;
import com.example.leadservice.dto.LeadRequest;
import com.example.leadservice.dto.LeadResponse;
import com.example.leadservice.dto.QualifyLeadRequest;
import com.example.leadservice.entity.Lead;
import com.example.leadservice.exception.LeadNotFoundException;
import com.example.leadservice.repository.LeadRepository;
import com.example.leadservice.service.LeadService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;

    // Constructor
    public LeadServiceImpl(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    // =========================================================
    // CREATE LEAD
    // =========================================================

    @Override
    public LeadResponse createLead(LeadRequest request) {

        // Check duplicate email
        if (leadRepository.existsByEmail(request.getEmail())) {

            throw new IllegalArgumentException(
                    "Lead already exists with email: "
                            + request.getEmail()
            );
        }

        Lead lead = new Lead();

        // Set request values
        lead.setFirstName(request.getFirstName());
        lead.setLastName(request.getLastName());
        lead.setEmail(request.getEmail());
        lead.setPhone(request.getPhone());
        lead.setCompany(request.getCompany());

        // Default values
        lead.setStatus("NEW");
        lead.setQualificationStatus("PENDING");

        // Date/time
        LocalDateTime now = LocalDateTime.now();

        lead.setCreatedAt(now);
        lead.setUpdatedAt(now);

        /*
         * Do NOT set ID manually.
         *
         * Lead.java should contain:
         *
         * @Id
         * @GeneratedValue(strategy = GenerationType.IDENTITY)
         *
         * MySQL will generate the ID.
         */

        Lead savedLead = leadRepository.save(lead);

        return mapToResponse(savedLead);
    }

    // =========================================================
    // GET ALL LEADS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadResponse> getAllLeads() {

        return leadRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // ASSIGN LEAD
    // =========================================================

    @Override
    public LeadResponse assignLead(
            Long id,
            AssignLeadRequest request) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() ->
                        new LeadNotFoundException(
                                "Lead not found with id: " + id
                        )
                );

        // assignedTo is Long in AssignLeadRequest
        lead.setAssignedTo(request.getAssignedTo());

        // Update status
        lead.setStatus("ASSIGNED");

        // Update timestamp
        lead.setUpdatedAt(LocalDateTime.now());

        Lead updatedLead = leadRepository.save(lead);

        return mapToResponse(updatedLead);
    }

    // =========================================================
    // QUALIFY LEAD
    // =========================================================

    @Override
    public LeadResponse qualifyLead(
            Long id,
            QualifyLeadRequest request) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() ->
                        new LeadNotFoundException(
                                "Lead not found with id: " + id
                        )
                );

        /*
         * QualifyLeadRequest contains:
         *
         * private Boolean qualified;
         *
         * Therefore we MUST use:
         *
         * request.getQualified()
         *
         * NOT:
         *
         * request.getQualificationStatus()
         */

        if (Boolean.TRUE.equals(request.getQualified())) {

            lead.setQualificationStatus("QUALIFIED");
            lead.setStatus("QUALIFIED");

        } else {

            lead.setQualificationStatus("DISQUALIFIED");
            lead.setStatus("DISQUALIFIED");
        }

        // Update timestamp
        lead.setUpdatedAt(LocalDateTime.now());

        Lead updatedLead = leadRepository.save(lead);

        return mapToResponse(updatedLead);
    }

    // =========================================================
    // ENTITY -> RESPONSE DTO
    // =========================================================

    private LeadResponse mapToResponse(Lead lead) {

        LeadResponse response = new LeadResponse();

        response.setId(lead.getId());
        response.setFirstName(lead.getFirstName());
        response.setLastName(lead.getLastName());
        response.setEmail(lead.getEmail());
        response.setPhone(lead.getPhone());
        response.setCompany(lead.getCompany());

        response.setAssignedTo(
                lead.getAssignedTo()
        );

        response.setQualificationStatus(
                lead.getQualificationStatus()
        );

        response.setStatus(
                lead.getStatus()
        );

        response.setCreatedAt(
                lead.getCreatedAt()
        );

        response.setUpdatedAt(
                lead.getUpdatedAt()
        );

        return response;
    }
}