package com.example.employee_helpdesk.repository;

import com.example.employee_helpdesk.entity.AssetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetAssignmentRepository
        extends JpaRepository<AssetAssignment, Long> {

    List<AssetAssignment> findByAssetIdOrderByAssignedAtDesc(Long assetId);

    Optional<AssetAssignment> findByAssetIdAndActiveTrue(Long assetId);

    List<AssetAssignment> findByEmployeeIdOrderByAssignedAtDesc(Long employeeId);
}