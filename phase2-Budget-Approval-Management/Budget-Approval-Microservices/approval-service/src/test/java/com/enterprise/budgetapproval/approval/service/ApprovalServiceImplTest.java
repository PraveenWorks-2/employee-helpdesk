package com.enterprise.budgetapproval.approval.service;

import com.enterprise.budgetapproval.approval.client.BudgetServiceClient;
import com.enterprise.budgetapproval.approval.dto.request.ApprovalDecisionRequest;
import com.enterprise.budgetapproval.approval.dto.response.ApprovalResponse;
import com.enterprise.budgetapproval.approval.entity.ApprovalRequest;
import com.enterprise.budgetapproval.approval.repository.ApprovalRequestRepository;
import com.enterprise.budgetapproval.approval.service.impl.ApprovalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApprovalServiceImplTest {

    @Mock
    private ApprovalRequestRepository approvalRequestRepository;

    @Mock
    private BudgetServiceClient budgetServiceClient;

    @InjectMocks
    private ApprovalServiceImpl approvalService;

    @Test
    void approveBudget_whenNoExistingRequest_createsAndApproves() {
        when(approvalRequestRepository.findByBudgetId(1L)).thenReturn(Optional.empty());
        when(approvalRequestRepository.save(any(ApprovalRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ApprovalDecisionRequest request = new ApprovalDecisionRequest();
        request.setRemarks("Looks good");

        ApprovalResponse response = approvalService.approveBudget(1L, request);

        assertEquals(ApprovalRequest.ApprovalStatus.APPROVED, response.getStatus());
        assertEquals(1L, response.getBudgetId());
        verify(budgetServiceClient, times(1)).updateBudgetStatus(1L, "APPROVED");
    }

    @Test
    void rejectBudget_whenExistingRequest_updatesAndRejects() {
        ApprovalRequest existing = ApprovalRequest.builder()
                .id(5L)
                .budgetId(2L)
                .status(ApprovalRequest.ApprovalStatus.PENDING)
                .build();

        when(approvalRequestRepository.findByBudgetId(2L)).thenReturn(Optional.of(existing));
        when(approvalRequestRepository.save(any(ApprovalRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ApprovalDecisionRequest request = new ApprovalDecisionRequest();
        request.setRemarks("Over budget");

        ApprovalResponse response = approvalService.rejectBudget(2L, request);

        assertEquals(ApprovalRequest.ApprovalStatus.REJECTED, response.getStatus());
        verify(budgetServiceClient, times(1)).updateBudgetStatus(2L, "REJECTED");
    }
}