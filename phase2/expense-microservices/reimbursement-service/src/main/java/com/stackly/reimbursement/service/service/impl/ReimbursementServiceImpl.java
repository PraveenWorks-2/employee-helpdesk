package com.stackly.reimbursement.service.service.impl;

import com.stackly.reimbursement.service.client.ExpenseClient;
import com.stackly.reimbursement.service.dto.ExpenseResponseDto;
import com.stackly.reimbursement.service.dto.ReimbursementResponseDto;
import com.stackly.reimbursement.service.entity.Reimbursement;
import com.stackly.reimbursement.service.enums.Status;
import com.stackly.reimbursement.service.mapper.ReimbursementMapper;
import com.stackly.reimbursement.service.repository.ReimbursementRepository;
import com.stackly.reimbursement.service.service.ReimbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ReimbursementServiceImpl implements ReimbursementService {

    public final ReimbursementRepository reimbursementRepository;
    public final ReimbursementMapper reimbursementMapper;
    public final ExpenseClient expenseClient;


    @Override
    public ReimbursementResponseDto approveReimbursement(Long expenseId) {

        ExpenseResponseDto expense = expenseClient.getExpenseById(expenseId);

        Reimbursement reimbursement=new Reimbursement();

        reimbursement.setExpenseId(expenseId);
        reimbursement.setAmount(expense.getAmount());
        reimbursement.setStatus(Status.APPROVED);
        reimbursement.setIsApproved(true);
        reimbursement.setIsRejected(false);
        reimbursement.setCreatedAt(LocalDateTime.now());
        reimbursement.setUpdatedAt(LocalDateTime.now());

        Reimbursement savedReimbursement = reimbursementRepository.save(reimbursement);
        return reimbursementMapper.mapToReimbursementResponseDto(savedReimbursement);
    }

    @Override
    public ReimbursementResponseDto rejectReimbursement(Long expenseId) {
        ExpenseResponseDto expense = expenseClient.getExpenseById(expenseId);

        Reimbursement reimbursement=new Reimbursement();

        reimbursement.setExpenseId(expenseId);
        reimbursement.setAmount(expense.getAmount());
        reimbursement.setStatus(Status.REJECTED);
        reimbursement.setIsApproved(false);
        reimbursement.setIsRejected(true);
        reimbursement.setCreatedAt(LocalDateTime.now());
        reimbursement.setUpdatedAt(LocalDateTime.now());

        Reimbursement savedReimbursement = reimbursementRepository.save(reimbursement);
        return reimbursementMapper.mapToReimbursementResponseDto(savedReimbursement);
    }

    @Override
    public ReimbursementResponseDto payReimbursement(Long expenseId) {
        ExpenseResponseDto expense = expenseClient.getExpenseById(expenseId);

        Reimbursement reimbursement=new Reimbursement();

        reimbursement.setExpenseId(expenseId);
        reimbursement.setAmount(expense.getAmount());
        reimbursement.setStatus(Status.APPROVED);
        reimbursement.setIsApproved(true);
        reimbursement.setIsPaid(true);
        reimbursement.setCreatedAt(LocalDateTime.now());
        reimbursement.setUpdatedAt(LocalDateTime.now());

        Reimbursement savedReimbursement = reimbursementRepository.save(reimbursement);
        return reimbursementMapper.mapToReimbursementResponseDto(savedReimbursement);
    }
}
