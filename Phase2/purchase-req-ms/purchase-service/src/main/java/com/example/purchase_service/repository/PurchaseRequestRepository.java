package com.example.purchase_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.purchase_service.entity.PurchaseRequest;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long>{

	Optional<PurchaseRequest> findByRequestNumber(String requestNumber);
}
