package com.enterprise.budgetapproval.budget.repository;

import com.enterprise.budgetapproval.budget.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByBudgetId(Long budgetId);
}