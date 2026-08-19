package com.example.approval_service.controller;

import com.example.approval_service.dto.ApprovalActionRequestDto;
import com.example.approval_service.dto.ApprovalResponseDto;
import com.example.approval_service.service.ApprovalService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(
            ApprovalService approvalService) {

        this.approvalService =
                approvalService;
    }

    @PostMapping("/{purchaseId}/approve")
    public ResponseEntity<ApprovalResponseDto>
    approvePurchase(
            @PathVariable Long purchaseId,
            @Valid @RequestBody
                    ApprovalActionRequestDto request) {

        return ResponseEntity.ok(
                approvalService.approvePurchase(
                        purchaseId,
                        request
                )
        );
    }

    @PostMapping("/{purchaseId}/reject")
    public ResponseEntity<ApprovalResponseDto>
    rejectPurchase(
            @PathVariable Long purchaseId,
            @Valid @RequestBody
                    ApprovalActionRequestDto request) {

        return ResponseEntity.ok(
                approvalService.rejectPurchase(
                        purchaseId,
                        request
                )
        );
    }
}