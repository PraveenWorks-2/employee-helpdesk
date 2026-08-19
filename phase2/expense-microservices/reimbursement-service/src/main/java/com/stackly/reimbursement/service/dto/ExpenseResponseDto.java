package com.stackly.reimbursement.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExpenseResponseDto {
    private Long id;
    private String employeeName;
    private String expenseType;
    private String description;
    private BigDecimal amount;
    private String status;
    private LocalDate submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
