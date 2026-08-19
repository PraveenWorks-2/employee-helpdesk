package com.stackly.expense.service.repository;

import com.stackly.expense.service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
