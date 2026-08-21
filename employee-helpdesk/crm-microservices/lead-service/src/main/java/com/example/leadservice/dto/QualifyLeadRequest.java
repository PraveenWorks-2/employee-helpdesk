package com.example.leadservice.dto;

import jakarta.validation.constraints.NotNull;

public class QualifyLeadRequest {

    @NotNull(message = "Qualified is required")
    private Boolean qualified;

    public QualifyLeadRequest() {
    }

    public Boolean getQualified() {
        return qualified;
    }

    public void setQualified(Boolean qualified) {
        this.qualified = qualified;
    }
}