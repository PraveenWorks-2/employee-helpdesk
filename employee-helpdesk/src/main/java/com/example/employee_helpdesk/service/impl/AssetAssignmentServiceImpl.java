package com.example.employee_helpdesk.service.impl;

import com.example.employee_helpdesk.dto.AssetAssignmentRequestDto;
import com.example.employee_helpdesk.dto.AssetAssignmentResponseDto;
import com.example.employee_helpdesk.entity.Asset;
import com.example.employee_helpdesk.entity.AssetAssignment;
import com.example.employee_helpdesk.enums.Statuses;
import com.example.employee_helpdesk.exception.BadRequestException;
import com.example.employee_helpdesk.exception.ResourceNotFoundException;
import com.example.employee_helpdesk.repository.AssetAssignmentRepository;
import com.example.employee_helpdesk.repository.AssetRepository;
import com.example.employee_helpdesk.service.AssetAssignmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetAssignmentServiceImpl implements AssetAssignmentService {

    private static final Logger log =
            LoggerFactory.getLogger(AssetAssignmentServiceImpl.class);

    private final AssetRepository assetRepository;
    private final AssetAssignmentRepository assetAssignmentRepository;

    @Override
    @Transactional
    public AssetAssignmentResponseDto assignAsset(
            Long assetId,
            AssetAssignmentRequestDto request) {

        log.info("Assigning asset {} to employee {}",
                assetId, request.getEmployeeId());

        Asset asset = assetRepository.findByIdAndIsDeletedFalse(assetId)
                .orElseThrow(() -> {
                    log.warn("Asset not found with id: {}", assetId);
                    return new ResourceNotFoundException(
                            "Asset not found with id: " + assetId);
                });

        if (asset.getStatus() != Statuses.AVAILABLE) {
            log.warn("Asset {} is not available. Current status: {}",
                    assetId, asset.getStatus());

            throw new BadRequestException(
                    "Asset is not available for assignment");
        }

        AssetAssignment assignment = AssetAssignment.builder()
                .asset(asset)
                .employeeId(request.getEmployeeId())
                .assignedAt(LocalDateTime.now())
                .returnedAt(null)
                .active(true)
                .build();

        asset.setStatus(Statuses.ASSIGNED);
        asset.setUpdatedAt(LocalDateTime.now().toLocalDate());

        assetRepository.save(asset);

        AssetAssignment savedAssignment =
                assetAssignmentRepository.save(assignment);

        log.info("Asset {} successfully assigned to employee {}",
                assetId, request.getEmployeeId());

        return mapToResponse(savedAssignment);
    }

    @Override
    @Transactional
    public AssetAssignmentResponseDto returnAsset(Long assetId) {

        log.info("Returning asset {}", assetId);

        Asset asset = assetRepository.findByIdAndIsDeletedFalse(assetId)
                .orElseThrow(() -> {
                    log.warn("Asset not found with id: {}", assetId);
                    return new ResourceNotFoundException(
                            "Asset not found with id: " + assetId);
                });

        AssetAssignment assignment =
                assetAssignmentRepository
                        .findByAssetIdAndActiveTrue(assetId)
                        .orElseThrow(() -> {
                            log.warn(
                                    "Active assignment not found for asset {}",
                                    assetId);

                            return new ResourceNotFoundException(
                                    "Active asset assignment not found");
                        });

        assignment.setReturnedAt(LocalDateTime.now());
        assignment.setActive(false);

        asset.setStatus(Statuses.AVAILABLE);
        asset.setUpdatedAt(LocalDateTime.now().toLocalDate());

        assetRepository.save(asset);

        AssetAssignment updatedAssignment =
                assetAssignmentRepository.save(assignment);

        log.info("Asset {} successfully returned by employee {}",
                assetId, assignment.getEmployeeId());

        return mapToResponse(updatedAssignment);
    }

    @Override
    public List<AssetAssignmentResponseDto> getAssetHistory(Long assetId) {

        log.info("Fetching assignment history for asset {}", assetId);

        if (!assetRepository.existsById(assetId)) {
            log.warn("Asset not found with id: {}", assetId);

            throw new ResourceNotFoundException(
                    "Asset not found with id: " + assetId);
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

        log.info("Fetching asset history for employee {}",
                employeeId);

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