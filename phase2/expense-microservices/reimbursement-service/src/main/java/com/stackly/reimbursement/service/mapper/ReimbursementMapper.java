package com.stackly.reimbursement.service.mapper;

import com.stackly.reimbursement.service.dto.ReimbursementResponseDto;
import com.stackly.reimbursement.service.entity.Reimbursement;
import org.springframework.stereotype.Component;

@Component
public class ReimbursementMapper {

    public ReimbursementResponseDto mapToReimbursementResponseDto (Reimbursement reimbursement){
        return new ReimbursementResponseDto(
                reimbursement.getId(),
                reimbursement.getExpenseId(),
                reimbursement.getAmount(),
                reimbursement.getStatus(),
                reimbursement.getIsApproved(),
                reimbursement.getIsRejected(),
                reimbursement.getIsPaid(),
                reimbursement.getCreatedAt(),
                reimbursement.getUpdatedAt()

        );
    }
}
