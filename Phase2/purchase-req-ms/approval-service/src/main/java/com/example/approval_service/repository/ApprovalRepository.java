package com.example.approval_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.approval_service.entity.Approval;


@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

	Optional<Approval> findByPurchaseId(Long purchaseId);
}
