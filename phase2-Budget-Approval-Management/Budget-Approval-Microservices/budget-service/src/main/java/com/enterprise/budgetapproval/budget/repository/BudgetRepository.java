package com.enterprise.budgetapproval.budget.repository;

import com.enterprise.budgetapproval.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByDepartmentName(String departmentName);

    List<Budget> findByStatus(Budget.BudgetStatus status);

    List<Budget> findByFiscalYear(Integer fiscalYear);
}