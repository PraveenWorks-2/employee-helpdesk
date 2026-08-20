package com.enterprise.budgetapproval.approval.repository;

import com.enterprise.budgetapproval.approval.entity.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {

    Optional<ApprovalRequest> findByBudgetId(Long budgetId);
}