package com.example.employee_helpdesk.controller;

import com.example.employee_helpdesk.dto.AssetAssignmentRequestDto;
import com.example.employee_helpdesk.dto.AssetAssignmentResponseDto;
import com.example.employee_helpdesk.service.AssetAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetAssignmentController {

    private final AssetAssignmentService assetAssignmentService;

    @PostMapping("/{assetId}/assign")
    public ResponseEntity<AssetAssignmentResponseDto> assignAsset(
            @PathVariable Long assetId,
            @Valid @RequestBody AssetAssignmentRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assetAssignmentService.assignAsset(assetId, request));
    }

    @PostMapping("/{assetId}/return")
    public ResponseEntity<AssetAssignmentResponseDto> returnAsset(
            @PathVariable Long assetId) {

        return ResponseEntity.ok(
                assetAssignmentService.returnAsset(assetId));
    }

    @GetMapping("/{assetId}/history")
    public ResponseEntity<List<AssetAssignmentResponseDto>> getAssetHistory(
            @PathVariable Long assetId) {

        return ResponseEntity.ok(
                assetAssignmentService.getAssetHistory(assetId));
    }

    @GetMapping("/employee/{employeeId}/history")
    public ResponseEntity<List<AssetAssignmentResponseDto>> getEmployeeAssets(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                assetAssignmentService.getEmployeeAssets(employeeId));
    }
}