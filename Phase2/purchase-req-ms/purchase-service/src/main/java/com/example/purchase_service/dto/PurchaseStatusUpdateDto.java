package com.example.purchase_service.dto;

import jakarta.validation.constraints.NotBlank;

public class PurchaseStatusUpdateDto {

    @NotBlank
    private String status;

    public PurchaseStatusUpdateDto() {
    }

    public PurchaseStatusUpdateDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}