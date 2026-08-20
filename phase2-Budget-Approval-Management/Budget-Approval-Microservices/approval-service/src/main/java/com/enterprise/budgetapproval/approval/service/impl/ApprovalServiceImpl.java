package com.enterprise.budgetapproval.approval.service.impl;

import com.enterprise.budgetapproval.approval.client.BudgetServiceClient;
import com.enterprise.budgetapproval.approval.dto.request.ApprovalDecisionRequest;
import com.enterprise.budgetapproval.approval.dto.response.ApprovalResponse;
import com.enterprise.budgetapproval.approval.entity.ApprovalRequest;
import com.enterprise.budgetapproval.approval.exception.ResourceNotFoundException;
import com.enterprise.budgetapproval.approval.repository.ApprovalRequestRepository;
import com.enterprise.budgetapproval.approval.service.ApprovalService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final BudgetServiceClient budgetServiceClient;

    public ApprovalServiceImpl(ApprovalRequestRepository approvalRequestRepository,
                                BudgetServiceClient budgetServiceClient) {
        this.approvalRequestRepository = approvalRequestRepository;
        this.budgetServiceClient = budgetServiceClient;
    }
    
    @Override
    public ApprovalResponse submitForApproval(Long budgetId) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findByBudgetId(budgetId)
                .orElseGet(() -> ApprovalRequest.builder()
                        .budgetId(budgetId)
                        .build());

        approvalRequest.setStatus(ApprovalRequest.ApprovalStatus.PENDING);
        approvalRequest.setRemarks(null);
        approvalRequest.setDecidedAt(null);
        ApprovalRequest saved = approvalRequestRepository.save(approvalRequest);

        return mapToResponse(saved);
    }

    @Override
    public ApprovalResponse approveBudget(Long budgetId, ApprovalDecisionRequest request) {
        return processDecision(budgetId, request, ApprovalRequest.ApprovalStatus.APPROVED);
    }

    @Override
    public ApprovalResponse rejectBudget(Long budgetId, ApprovalDecisionRequest request) {
        return processDecision(budgetId, request, ApprovalRequest.ApprovalStatus.REJECTED);
    }

    private ApprovalResponse processDecision(Long budgetId, ApprovalDecisionRequest request,
                                               ApprovalRequest.ApprovalStatus status) {
    	ApprovalRequest approvalRequest = approvalRequestRepository.findByBudgetId(budgetId)
    	        .orElseGet(() -> ApprovalRequest.builder()
    	                .budgetId(budgetId)
    	                .build());

        approvalRequest.setStatus(status);
        approvalRequest.setRemarks(request.getRemarks());
        approvalRequest.setDecidedAt(LocalDateTime.now());

        ApprovalRequest saved = approvalRequestRepository.save(approvalRequest);

        budgetServiceClient.updateBudgetStatus(budgetId, status.name());

        return mapToResponse(saved);
    }

    private ApprovalResponse mapToResponse(ApprovalRequest approvalRequest) {
        return ApprovalResponse.builder()
                .id(approvalRequest.getId())
                .budgetId(approvalRequest.getBudgetId())
                .status(approvalRequest.getStatus())
                .remarks(approvalRequest.getRemarks())
                .requestedAt(approvalRequest.getRequestedAt())
                .decidedAt(approvalRequest.getDecidedAt())
                .build();
    }
}