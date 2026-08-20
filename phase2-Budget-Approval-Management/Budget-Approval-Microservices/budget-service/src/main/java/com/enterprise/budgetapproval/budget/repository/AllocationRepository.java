package com.enterprise.budgetapproval.budget.repository;

import com.enterprise.budgetapproval.budget.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    List<Allocation> findByBudgetId(Long budgetId);
}