package com.enterprise.budgetapproval.budget.service;

import com.enterprise.budgetapproval.budget.dto.request.AllocationRequest;
import com.enterprise.budgetapproval.budget.dto.request.BudgetRequest;
import com.enterprise.budgetapproval.budget.dto.request.TransactionRequest;
import com.enterprise.budgetapproval.budget.dto.response.AllocationResponse;
import com.enterprise.budgetapproval.budget.dto.response.BudgetResponse;
import com.enterprise.budgetapproval.budget.dto.response.TransactionResponse;
import com.enterprise.budgetapproval.budget.dto.response.VarianceResponse;
import com.enterprise.budgetapproval.budget.entity.Budget;

public interface BudgetService {

    BudgetResponse createBudget(BudgetRequest request);

    BudgetResponse getBudgetById(Long id);

    AllocationResponse addAllocation(Long budgetId, AllocationRequest request);

    VarianceResponse getVariance(Long budgetId);

    BudgetResponse updateStatus(Long budgetId, Budget.BudgetStatus status);
    
    TransactionResponse addTransaction(Long budgetId, TransactionRequest request);
}