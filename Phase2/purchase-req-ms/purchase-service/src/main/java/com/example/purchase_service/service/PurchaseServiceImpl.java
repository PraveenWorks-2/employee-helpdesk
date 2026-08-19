package com.example.purchase_service.service;

import com.example.purchase_service.dto.PurchaseItemRequestDto;
import com.example.purchase_service.dto.PurchaseItemResponseDto;
import com.example.purchase_service.dto.PurchaseRequestDto;
import com.example.purchase_service.dto.PurchaseResponseDto;
import com.example.purchase_service.entity.PurchaseItem;
import com.example.purchase_service.entity.PurchaseRequest;
import com.example.purchase_service.exception.ResourceNotFoundException;
import com.example.purchase_service.repository.PurchaseRequestRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    public PurchaseServiceImpl(
            PurchaseRequestRepository purchaseRequestRepository) {

        this.purchaseRequestRepository =
                purchaseRequestRepository;
    }

    @Override
    @Transactional
    public PurchaseResponseDto createPurchase(
            PurchaseRequestDto request) {

        PurchaseRequest purchaseRequest =
                new PurchaseRequest();

        purchaseRequest.setRequestNumber(
                generateRequestNumber()
        );

        purchaseRequest.setEmployeeId(
                request.getEmployeeId()
        );

        purchaseRequest.setTitle(
                request.getTitle()
        );

        purchaseRequest.setDescription(
                request.getDescription()
        );

        purchaseRequest.setStatus("PENDING");

        LocalDateTime now = LocalDateTime.now();

        purchaseRequest.setCreatedAt(now);
        purchaseRequest.setUpdatedAt(now);

        BigDecimal totalAmount =
                BigDecimal.ZERO;

        List<PurchaseItem> items =
                new ArrayList<>();

        for (PurchaseItemRequestDto itemRequest
                : request.getItems()) {

            PurchaseItem item =
                    new PurchaseItem();

            item.setItemName(
                    itemRequest.getItemName()
            );

            item.setDescription(
                    itemRequest.getDescription()
            );

            item.setQuantity(
                    itemRequest.getQuantity()
            );

            item.setUnitPrice(
                    itemRequest.getUnitPrice()
            );

            item.setPurchaseRequest(
                    purchaseRequest
            );

            BigDecimal itemTotal =
                    itemRequest
                            .getUnitPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemRequest
                                                    .getQuantity()
                                    )
                            );

            totalAmount =
                    totalAmount.add(itemTotal);

            items.add(item);
        }

        purchaseRequest.setTotalAmount(
                totalAmount
        );

        purchaseRequest.setItems(items);

        PurchaseRequest saved =
                purchaseRequestRepository.save(
                        purchaseRequest
                );

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponseDto> getAllPurchases() {

        return purchaseRequestRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseResponseDto getPurchaseById(
            Long id) {

        PurchaseRequest purchaseRequest =
                purchaseRequestRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Purchase request not found: "
                                                + id
                                )
                        );

        return mapToResponse(purchaseRequest);
    }

    private String generateRequestNumber() {

        return "PR-" +
                System.currentTimeMillis();
    }

    private PurchaseResponseDto mapToResponse(
            PurchaseRequest purchaseRequest) {

        PurchaseResponseDto response = new PurchaseResponseDto();

        response.setId(
                purchaseRequest.getId());
        response.setRequestNumber(
                purchaseRequest.getRequestNumber());
        response.setEmployeeId(
                purchaseRequest.getEmployeeId());
        response.setTitle(
                purchaseRequest.getTitle());
        response.setDescription(
                purchaseRequest.getDescription());
        response.setTotalAmount(purchaseRequest.getTotalAmount());
        response.setStatus(purchaseRequest.getStatus());
        response.setCreatedAt(purchaseRequest.getCreatedAt());
        response.setUpdatedAt(purchaseRequest.getUpdatedAt());

        List<PurchaseItemResponseDto>
                itemResponses =
                purchaseRequest
                        .getItems()
                        .stream()
                        .map(item -> {

                            PurchaseItemResponseDto
                                    itemResponse =
                                    new PurchaseItemResponseDto();

                            itemResponse.setId(
                                    item.getId()
                            );

                            itemResponse.setItemName(
                                    item.getItemName()
                            );

                            itemResponse.setDescription(
                                    item.getDescription()
                            );

                            itemResponse.setQuantity(
                                    item.getQuantity()
                            );

                            itemResponse.setUnitPrice(
                                    item.getUnitPrice()
                            );

                            return itemResponse;
                        })
                        .toList();

        response.setItems(itemResponses);

        return response;
    }

    @Override
    @Transactional
    public PurchaseResponseDto updatePurchaseStatus(
            Long id,
            String status) {

        PurchaseRequest purchaseRequest =
                purchaseRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Purchase request not found: " + id));

        purchaseRequest.setStatus(status);
        purchaseRequest.setUpdatedAt(LocalDateTime.now());

        PurchaseRequest updated =
                purchaseRequestRepository.save(purchaseRequest);

        return mapToResponse(updated);
    }
    
}