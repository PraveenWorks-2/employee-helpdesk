package com.stackly.reimbursement.service.dto;

import com.stackly.reimbursement.service.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReimbursementResponseDto {

    private Long id;

    private Long expenseId;

    private BigDecimal amount;

    private Status status;

    private Boolean isApproved;

    private Boolean isRejected;

    private Boolean isPaid;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
