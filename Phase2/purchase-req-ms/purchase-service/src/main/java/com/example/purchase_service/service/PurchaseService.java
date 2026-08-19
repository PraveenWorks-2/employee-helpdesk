package com.example.purchase_service.service;

import com.example.purchase_service.dto.PurchaseRequestDto;
import com.example.purchase_service.dto.PurchaseResponseDto;

import java.util.List;

public interface PurchaseService {

    PurchaseResponseDto createPurchase(PurchaseRequestDto request);

    List<PurchaseResponseDto> getAllPurchases();

    PurchaseResponseDto getPurchaseById(Long id);
    PurchaseResponseDto updatePurchaseStatus(Long id, String status);
}