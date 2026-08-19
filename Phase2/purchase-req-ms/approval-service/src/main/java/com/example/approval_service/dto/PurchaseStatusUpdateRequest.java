package com.example.approval_service.dto;

public class PurchaseStatusUpdateRequest {

    private String status;

    public PurchaseStatusUpdateRequest() {
    }

    public PurchaseStatusUpdateRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}