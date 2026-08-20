package com.enterprise.budgetapproval.budget.dto.response;

import com.enterprise.budgetapproval.budget.entity.Allocation;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AllocationResponse {

    private Long id;
    private Long budgetId;
    private String category;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public static AllocationResponse fromEntity(Allocation allocation) {
        return AllocationResponse.builder()
                .id(allocation.getId())
                .budgetId(allocation.getBudget().getId())
                .category(allocation.getCategory())
                .amount(allocation.getAmount())
                .createdAt(allocation.getCreatedAt())
                .build();
    }
}