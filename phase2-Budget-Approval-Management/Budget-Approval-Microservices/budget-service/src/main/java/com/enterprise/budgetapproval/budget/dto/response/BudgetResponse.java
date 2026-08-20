package com.enterprise.budgetapproval.budget.dto.response;

import com.enterprise.budgetapproval.budget.entity.Budget;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BudgetResponse {

    private Long id;
    private String departmentName;
    private Integer fiscalYear;
    private BigDecimal totalAmount;
    private BigDecimal spentAmount;
    private Budget.BudgetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BudgetResponse fromEntity(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .departmentName(budget.getDepartmentName())
                .fiscalYear(budget.getFiscalYear())
                .totalAmount(budget.getTotalAmount())
                .spentAmount(budget.getSpentAmount())
                .status(budget.getStatus())
                .createdAt(budget.getCreatedAt())
                .updatedAt(budget.getUpdatedAt())
                .build();
    }
}