package com.enterprise.budgetapproval.approval.controller;

import com.enterprise.budgetapproval.approval.dto.request.ApprovalDecisionRequest;
import com.enterprise.budgetapproval.approval.dto.response.ApprovalResponse;
import com.enterprise.budgetapproval.approval.service.ApprovalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }
    
    @PreAuthorize("hasRole('FINANCE_ADMIN')")
    @PostMapping("/{budgetId}/approve")
    public ResponseEntity<ApprovalResponse> approveBudget(
            @PathVariable Long budgetId,
            @Valid @RequestBody(required = false) ApprovalDecisionRequest request) {
        ApprovalResponse response = approvalService.approveBudget(
                budgetId, request != null ? request : new ApprovalDecisionRequest());
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasRole('FINANCE_ADMIN')")
    @PostMapping("/{budgetId}/reject")
    public ResponseEntity<ApprovalResponse> rejectBudget(
            @PathVariable Long budgetId,
            @Valid @RequestBody(required = false) ApprovalDecisionRequest request) {
        ApprovalResponse response = approvalService.rejectBudget(
                budgetId, request != null ? request : new ApprovalDecisionRequest());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{budgetId}/submit")
    public ResponseEntity<ApprovalResponse> submitForApproval(@PathVariable Long budgetId) {
        ApprovalResponse response = approvalService.submitForApproval(budgetId);
        return ResponseEntity.ok(response);
    }
    
    
}