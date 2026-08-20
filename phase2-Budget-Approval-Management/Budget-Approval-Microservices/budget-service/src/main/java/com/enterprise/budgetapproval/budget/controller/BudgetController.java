package com.enterprise.budgetapproval.budget.controller;

import com.enterprise.budgetapproval.budget.dto.request.AllocationRequest;
import com.enterprise.budgetapproval.budget.dto.request.BudgetRequest;
import com.enterprise.budgetapproval.budget.dto.request.TransactionRequest;
import com.enterprise.budgetapproval.budget.dto.response.AllocationResponse;
import com.enterprise.budgetapproval.budget.dto.response.BudgetResponse;
import com.enterprise.budgetapproval.budget.dto.response.TransactionResponse;
import com.enterprise.budgetapproval.budget.dto.response.VarianceResponse;
import com.enterprise.budgetapproval.budget.entity.Budget;
import com.enterprise.budgetapproval.budget.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }
    
    @PreAuthorize("hasRole('FINANCE_ADMIN')")
    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody BudgetRequest request) {
        BudgetResponse response = budgetService.createBudget(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudget(@PathVariable Long id) {
        BudgetResponse response = budgetService.getBudgetById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/allocate")
    public ResponseEntity<AllocationResponse> allocateBudget(
            @PathVariable Long id,
            @Valid @RequestBody AllocationRequest request) {
        AllocationResponse response = budgetService.addAllocation(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/variance")
    public ResponseEntity<VarianceResponse> getVariance(@PathVariable Long id) {
        VarianceResponse response = budgetService.getVariance(id);
        return ResponseEntity.ok(response);
    }

    // Internal endpoint - called by approval-service to update budget status
    @PutMapping("/{id}/status")
    public ResponseEntity<BudgetResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam Budget.BudgetStatus status) {
        BudgetResponse response = budgetService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/transactions")
    public ResponseEntity<TransactionResponse> addTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = budgetService.addTransaction(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}