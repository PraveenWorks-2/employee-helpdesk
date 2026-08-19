package com.stackly.expense.service.dto;

import com.stackly.expense.service.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExpenseResponseDto {

    private Long id;

    private String employeeName;

    private String expenseType;

    private String description;

    private BigDecimal amount;

    private Status status;

    private LocalDate submittedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
