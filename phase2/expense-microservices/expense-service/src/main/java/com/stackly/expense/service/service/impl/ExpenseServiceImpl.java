package com.stackly.expense.service.service.impl;

import com.stackly.expense.service.dto.ExpenseRequestDto;
import com.stackly.expense.service.dto.ExpenseResponseDto;
import com.stackly.expense.service.entity.Expense;
import com.stackly.expense.service.enums.Status;
import com.stackly.expense.service.exception.ExpenseNotFoundException;
import com.stackly.expense.service.exception.InvalidExpenseStatusException;
import com.stackly.expense.service.mapper.ExpenseMapper;
import com.stackly.expense.service.repository.ExpenseRepository;
import com.stackly.expense.service.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;


    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto) {
        Expense expense = ExpenseMapper.mapToExpenses(expenseRequestDto);

        expense.setStatus(Status.DRAFT);
        expense.setCreatedAt(LocalDateTime.now());
        expense.setUpdatedAt(LocalDateTime.now());

        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseResponseDto(savedExpense);
    }

    @Override
    public ExpenseResponseDto getById(Long id) {
        Expense expense = expenseRepository.findById(id).
                orElseThrow(()->new ExpenseNotFoundException(
                        "Expense not found with id: " + id));
        return ExpenseMapper.mapToExpenseResponseDto(expense);
    }

    @Override
    public ExpenseResponseDto submitExpense(Long id) {
        Expense expense = expenseRepository.findById(id).
                orElseThrow(()-> new ExpenseNotFoundException(
                        "Expense not found with id: " + id));
        if (expense.getStatus() != Status.DRAFT) {
            throw new InvalidExpenseStatusException(
                    "Expense cannot be submitted because its current status is "
                            + expense.getStatus());
        }
        expense.setStatus(Status.SUBMITTED);
        expense.setSubmittedAt(LocalDate.now());
        expense.setUpdatedAt(LocalDateTime.now());

        Expense updatedExpense = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseResponseDto(updatedExpense);
    }
}
