package com.example.employee_helpdesk.service;

import com.example.employee_helpdesk.dto.AssetAssignmentRequestDto;
import com.example.employee_helpdesk.dto.AssetAssignmentResponseDto;

import java.util.List;

public interface AssetAssignmentService {

    AssetAssignmentResponseDto assignAsset(
            Long assetId,
            AssetAssignmentRequestDto request);

    AssetAssignmentResponseDto returnAsset(Long assetId);

    List<AssetAssignmentResponseDto> getAssetHistory(Long assetId);

    List<AssetAssignmentResponseDto> getEmployeeAssets(Long employeeId);
}