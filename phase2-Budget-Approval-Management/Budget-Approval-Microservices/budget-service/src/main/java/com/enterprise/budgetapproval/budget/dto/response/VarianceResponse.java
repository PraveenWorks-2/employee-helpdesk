package com.enterprise.budgetapproval.budget.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class VarianceResponse {

    private Long budgetId;
    private String departmentName;
    private BigDecimal totalAmount;
    private BigDecimal spentAmount;
    private BigDecimal variance;
    private BigDecimal variancePercentage;
}