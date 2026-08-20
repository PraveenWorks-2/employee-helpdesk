package com.enterprise.budgetapproval.budget.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BudgetRequest {

    @NotBlank(message = "Department name is required")
    private String departmentName;

    @NotNull(message = "Fiscal year is required")
    private Integer fiscalYear;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be greater than zero")
    private BigDecimal totalAmount;
}