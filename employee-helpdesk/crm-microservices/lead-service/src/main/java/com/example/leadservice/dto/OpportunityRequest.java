package com.example.leadservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OpportunityRequest {

    @NotNull(message = "Lead ID is required")
    private Long leadId;

    @NotBlank(message = "Opportunity name is required")
    private String name;

    private String description;

    private Double amount;

    public OpportunityRequest() {
    }

    public OpportunityRequest(
            Long leadId,
            String name,
            String description,
            Double amount) {

        this.leadId = leadId;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}