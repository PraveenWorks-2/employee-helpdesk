package com.example.leadservice.dto;

import jakarta.validation.constraints.NotNull;

public class AssignLeadRequest {

    @NotNull
    private Long assignedTo;

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }
}