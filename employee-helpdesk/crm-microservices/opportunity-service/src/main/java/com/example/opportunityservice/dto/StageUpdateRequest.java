package com.example.opportunityservice.dto;

import com.example.opportunityservice.entity.OpportunityStage;
import jakarta.validation.constraints.NotNull;

public class StageUpdateRequest {

    @NotNull(message = "Stage is required")
    private OpportunityStage stage;

    public OpportunityStage getStage() {
        return stage;
    }

    public void setStage(OpportunityStage stage) {
        this.stage = stage;
    }
}