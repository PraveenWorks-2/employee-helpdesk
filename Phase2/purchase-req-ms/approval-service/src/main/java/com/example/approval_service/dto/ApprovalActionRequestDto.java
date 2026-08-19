package com.example.approval_service.dto;

import jakarta.validation.constraints.NotNull;

public class ApprovalActionRequestDto {

    @NotNull
    private Long managerId;

    private String comments;

    public ApprovalActionRequestDto() {
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}