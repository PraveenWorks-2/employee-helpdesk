package com.example.employee_helpdesk.controller;

import com.example.employee_helpdesk.dto.AssetRequestDto;
import com.example.employee_helpdesk.dto.AssetResponseDto;
import com.example.employee_helpdesk.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetResponseDto> createAsset(
            @Valid @RequestBody AssetRequestDto assetRequestDto) {

        AssetResponseDto asset =
                assetService.createAsset(assetRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(asset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDto> getAssetById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assetService.getAssetById(id));
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDto>> getAllAssets() {

        return ResponseEntity.ok(
                assetService.getAllAssets());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDto> updateAsset(
            @PathVariable Long id,
            @Valid @RequestBody AssetRequestDto assetRequestDto) {

        return ResponseEntity.ok(
                assetService.updateAsset(id, assetRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(
            @PathVariable Long id) {

        assetService.deleteAsset(id);

        return ResponseEntity.ok("Asset deleted successfully");
    }
}