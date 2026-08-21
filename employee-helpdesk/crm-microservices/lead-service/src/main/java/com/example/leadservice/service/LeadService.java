package com.example.leadservice.service;

import com.example.leadservice.dto.AssignLeadRequest;
import com.example.leadservice.dto.LeadRequest;
import com.example.leadservice.dto.LeadResponse;
import com.example.leadservice.dto.QualifyLeadRequest;

import java.util.List;

public interface LeadService {

    LeadResponse createLead(LeadRequest request);

    List<LeadResponse> getAllLeads();

    LeadResponse assignLead(Long id, AssignLeadRequest request);

    LeadResponse qualifyLead(Long id, QualifyLeadRequest request);
}