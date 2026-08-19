package com.stackly.expense.service.service;

import com.stackly.expense.service.dto.ExpenseRequestDto;
import com.stackly.expense.service.dto.ExpenseResponseDto;

public interface ExpenseService {

    ExpenseResponseDto createExpense (ExpenseRequestDto expenseRequestDto);

    ExpenseResponseDto getById (Long id);

    ExpenseResponseDto submitExpense(Long id);


}
