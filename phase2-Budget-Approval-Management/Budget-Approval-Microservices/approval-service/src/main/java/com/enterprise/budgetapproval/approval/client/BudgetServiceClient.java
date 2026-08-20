package com.enterprise.budgetapproval.approval.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "budget-service")
public interface BudgetServiceClient {

    @PutMapping("/api/budgets/{id}/status")
    void updateBudgetStatus(@PathVariable("id") Long id, @RequestParam("status") String status);
}