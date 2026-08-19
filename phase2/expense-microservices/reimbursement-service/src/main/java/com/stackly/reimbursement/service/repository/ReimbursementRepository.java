package com.stackly.reimbursement.service.repository;

import com.stackly.reimbursement.service.entity.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {

    Optional<Reimbursement> findByExpenseId(Long expenseId);
}
