package com.enterprise.budgetapproval.budget.service.impl;

import com.enterprise.budgetapproval.budget.dto.request.AllocationRequest;
import com.enterprise.budgetapproval.budget.dto.request.BudgetRequest;
import com.enterprise.budgetapproval.budget.dto.request.TransactionRequest;
import com.enterprise.budgetapproval.budget.dto.response.AllocationResponse;
import com.enterprise.budgetapproval.budget.dto.response.BudgetResponse;
import com.enterprise.budgetapproval.budget.dto.response.TransactionResponse;
import com.enterprise.budgetapproval.budget.dto.response.VarianceResponse;
import com.enterprise.budgetapproval.budget.entity.Allocation;
import com.enterprise.budgetapproval.budget.entity.Budget;
import com.enterprise.budgetapproval.budget.exception.InvalidBudgetOperationException;
import com.enterprise.budgetapproval.budget.exception.ResourceNotFoundException;
import com.enterprise.budgetapproval.budget.repository.AllocationRepository;
import com.enterprise.budgetapproval.budget.repository.BudgetRepository;
import com.enterprise.budgetapproval.budget.repository.TransactionRepository;
import com.enterprise.budgetapproval.budget.service.BudgetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.enterprise.budgetapproval.budget.entity.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BudgetServiceImpl implements BudgetService {

	private final BudgetRepository budgetRepository;
	private final AllocationRepository allocationRepository;
	private final TransactionRepository transactionRepository;

	public BudgetServiceImpl(BudgetRepository budgetRepository,
	                          AllocationRepository allocationRepository,
	                          TransactionRepository transactionRepository) {
	    this.budgetRepository = budgetRepository;
	    this.allocationRepository = allocationRepository;
	    this.transactionRepository = transactionRepository;
	}

    @Override
    @Transactional
    public BudgetResponse createBudget(BudgetRequest request) {
        Budget budget = new Budget();
        budget.setDepartmentName(request.getDepartmentName());
        budget.setFiscalYear(request.getFiscalYear());
        budget.setTotalAmount(request.getTotalAmount());
        budget.setSpentAmount(BigDecimal.ZERO);
        budget.setStatus(Budget.BudgetStatus.PENDING);

        Budget saved = budgetRepository.save(budget);
        return BudgetResponse.fromEntity(saved);
    }

    @Override
    public BudgetResponse getBudgetById(Long id) {
        Budget budget = findBudgetOrThrow(id);
        return BudgetResponse.fromEntity(budget);
    }

    @Override
    @Transactional
    public AllocationResponse addAllocation(Long budgetId, AllocationRequest request) {
        Budget budget = findBudgetOrThrow(budgetId);

        Allocation allocation = new Allocation();
        allocation.setBudget(budget);
        allocation.setCategory(request.getCategory());
        allocation.setAmount(request.getAmount());

        Allocation saved = allocationRepository.save(allocation);
        return AllocationResponse.fromEntity(saved);
    }

    @Override
    public VarianceResponse getVariance(Long budgetId) {
        Budget budget = findBudgetOrThrow(budgetId);

        BigDecimal totalAmount = budget.getTotalAmount();
        BigDecimal spentAmount = budget.getSpentAmount() != null ? budget.getSpentAmount() : BigDecimal.ZERO;
        BigDecimal variance = totalAmount.subtract(spentAmount);

        BigDecimal variancePercentage = BigDecimal.ZERO;
        if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
            variancePercentage = variance
                    .divide(totalAmount, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        return VarianceResponse.builder()
                .budgetId(budget.getId())
                .departmentName(budget.getDepartmentName())
                .totalAmount(totalAmount)
                .spentAmount(spentAmount)
                .variance(variance)
                .variancePercentage(variancePercentage)
                .build();
    }

    @Override
    @Transactional
    public BudgetResponse updateStatus(Long budgetId, Budget.BudgetStatus status) {
        Budget budget = findBudgetOrThrow(budgetId);

        if (budget.getStatus() != Budget.BudgetStatus.PENDING) {
            throw new InvalidBudgetOperationException(
                    "Budget with id " + budgetId + " has already been " + budget.getStatus()
                            + " and cannot be changed again");
        }

        budget.setStatus(status);
        Budget saved = budgetRepository.save(budget);
        return BudgetResponse.fromEntity(saved);
    }
    
    @Override
    @Transactional
    public TransactionResponse addTransaction(Long budgetId, TransactionRequest request) {
        Budget budget = findBudgetOrThrow(budgetId);

        if (budget.getStatus() != Budget.BudgetStatus.APPROVED) {
            throw new InvalidBudgetOperationException(
                    "Budget with id " + budgetId + " is not approved and cannot accept transactions");
        }

        Transaction transaction = new Transaction();
        transaction.setBudget(budget);
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());

        Transaction savedTransaction = transactionRepository.save(transaction);

        BigDecimal newSpent = (budget.getSpentAmount() != null ? budget.getSpentAmount() : BigDecimal.ZERO)
                .add(request.getAmount());
        budget.setSpentAmount(newSpent);
        budgetRepository.save(budget);

        return TransactionResponse.fromEntity(savedTransaction);
    }

    private Budget findBudgetOrThrow(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
    }
}