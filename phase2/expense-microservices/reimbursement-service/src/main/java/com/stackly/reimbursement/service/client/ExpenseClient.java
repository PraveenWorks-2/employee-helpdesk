package com.stackly.reimbursement.service.client;

import com.stackly.reimbursement.service.dto.ExpenseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "expense-service")
public interface ExpenseClient {
    @GetMapping("/api/expenses/{id}")
    ExpenseResponseDto getExpenseById(@PathVariable("id") Long id);
}
