package com.enterprise.budgetapproval.approval.service;

import com.enterprise.budgetapproval.approval.dto.request.ApprovalDecisionRequest;
import com.enterprise.budgetapproval.approval.dto.response.ApprovalResponse;

public interface ApprovalService {
	
	ApprovalResponse submitForApproval(Long budgetId);

    ApprovalResponse approveBudget(Long budgetId, ApprovalDecisionRequest request);

    ApprovalResponse rejectBudget(Long budgetId, ApprovalDecisionRequest request);
}