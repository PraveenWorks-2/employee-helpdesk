package com.example.purchase_service.controller;

import com.example.purchase_service.dto.PurchaseRequestDto;
import com.example.purchase_service.dto.PurchaseResponseDto;
import com.example.purchase_service.dto.PurchaseStatusUpdateDto;
import com.example.purchase_service.service.PurchaseService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDto> createPurchase(
            @Valid @RequestBody PurchaseRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(purchaseService.createPurchase(request));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDto>> getAllPurchases() {

        return ResponseEntity.ok(
                purchaseService.getAllPurchases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDto> getPurchaseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseService.getPurchaseById(id));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseResponseDto> updatePurchaseStatus(
            @PathVariable Long id,
            @Valid @RequestBody PurchaseStatusUpdateDto request) {

        return ResponseEntity.ok(
                purchaseService.updatePurchaseStatus(
                        id,
                        request.getStatus()));
    }
}