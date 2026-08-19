package com.stackly.expense.service.mapper;

import com.stackly.expense.service.dto.ExpenseRequestDto;
import com.stackly.expense.service.dto.ExpenseResponseDto;
import com.stackly.expense.service.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public static Expense mapToExpenses(ExpenseRequestDto dto) {
        Expense expense = new Expense();

        expense.setEmployeeName(dto.getEmployeeName());
        expense.setExpenseType(dto.getExpenseType());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());

        return expense;
    }

    public static ExpenseResponseDto mapToExpenseResponseDto (Expense expense){
        return new ExpenseResponseDto(
                expense.getId(),
                expense.getEmployeeName(),
                expense.getExpenseType(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getStatus(),
                expense.getSubmittedAt(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }

}
