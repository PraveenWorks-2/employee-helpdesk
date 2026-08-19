package com.stackly.reimbursement.service.service;

import com.stackly.reimbursement.service.dto.ReimbursementResponseDto;

public interface ReimbursementService {

    ReimbursementResponseDto approveReimbursement (Long expenseId);

    ReimbursementResponseDto rejectReimbursement (Long expenseId);

    ReimbursementResponseDto payReimbursement (Long expenseId);

}
