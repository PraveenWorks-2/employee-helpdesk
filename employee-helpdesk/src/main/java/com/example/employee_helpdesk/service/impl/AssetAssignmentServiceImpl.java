
package com.example.employee_helpdesk.service.impl;

import com.example.employee_helpdesk.dto.AssetAssignmentRequestDto;
import com.example.employee_helpdesk.dto.AssetAssignmentResponseDto;
import com.example.employee_helpdesk.entity.Asset;
import com.example.employee_helpdesk.entity.AssetAssignment;
import com.example.employee_helpdesk.enums.Statuses;
import com.example.employee_helpdesk.repository.AssetAssignmentRepository;
import com.example.employee_helpdesk.repository.AssetRepository;
import com.example.employee_helpdesk.service.AssetAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetAssignmentServiceImpl implements AssetAssignmentService {

    private final AssetRepository assetRepository;
    private final AssetAssignmentRepository assetAssignmentRepository;

    @Override
    @Transactional
    public AssetAssignmentResponseDto assignAsset(
            Long assetId,
            AssetAssignmentRequestDto request) {

        Asset asset = assetRepository.findByIdAndIsDeletedFalse(assetId)
                .orElseThrow(() ->
                        new RuntimeException("Asset not found"));

        if (asset.getStatus() != Statuses.AVAILABLE) {
            throw new RuntimeException(
                    "Asset is not available for assignment");
        }

        AssetAssignment assignment = AssetAssignment.builder()
                .asset(asset)
                .employeeId(request.getEmployeeId())
                .assignedAt(LocalDateTime.now())
                .active(true)
                .build();

        asset.setStatus(Statuses.ASSIGNED);
        asset.setUpdatedAt(LocalDateTime.now().toLocalDate());

        assetRepository.save(asset);

        AssetAssignment savedAssignment =
                assetAssignmentRepository.save(assignment);

        return mapToResponse(savedAssignment);
    }

    @Override
    @Transactional
    public AssetAssignmentResponseDto returnAsset(Long assetId) {

        Asset asset = assetRepository.findByIdAndIsDeletedFalse(assetId)
                .orElseThrow(() ->
                        new RuntimeException("Asset not found"));

        AssetAssignment assignment =
                assetAssignmentRepository
                        .findByAssetIdAndActiveTrue(assetId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Active asset assignment not found"));

        assignment.setReturnedAt(LocalDateTime.now());
        assignment.setActive(false);

        asset.setStatus(Statuses.AVAILABLE);
        asset.setUpdatedAt(LocalDateTime.now().toLocalDate());

        assetRepository.save(asset);

        AssetAssignment updatedAssignment =
                assetAssignmentRepository.save(assignment);

        return mapToResponse(updatedAssignment);
    }

    @Override
    public List<AssetAssignmentResponseDto> getAssetHistory(Long assetId) {

        if (!assetRepository.existsById(assetId)) {
            throw new RuntimeException("Asset not found");
        }

        return assetAssignmentRepository
                .findByAssetIdOrderByAssignedAtDesc(assetId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AssetAssignmentResponseDto> getEmployeeAssets(
            Long employeeId) {

        return assetAssignmentRepository
                .findByEmployeeIdOrderByAssignedAtDesc(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AssetAssignmentResponseDto mapToResponse(
            AssetAssignment assignment) {

        Asset asset = assignment.getAsset();

        return new AssetAssignmentResponseDto(
                assignment.getId(),
                asset.getId(),
                asset.getAssetCode(),
                assignment.getEmployeeId(),
                assignment.getAssignedAt(),
                assignment.getReturnedAt(),
                assignment.getActive()
        );
    }
}