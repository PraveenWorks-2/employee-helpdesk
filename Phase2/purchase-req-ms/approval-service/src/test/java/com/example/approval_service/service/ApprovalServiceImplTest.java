package com.example.approval_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.approval_service.client.PurchaseServiceClient;
import com.example.approval_service.dto.ApprovalActionRequestDto;
import com.example.approval_service.dto.ApprovalResponseDto;
import com.example.approval_service.entity.Approval;
import com.example.approval_service.repository.ApprovalRepository;

@ExtendWith(MockitoExtension.class)
class ApprovalServiceImplTest {

    @Mock
    private ApprovalRepository approvalRepository;

    @Mock
    private PurchaseServiceClient purchaseServiceClient;

    @InjectMocks
    private ApprovalServiceImpl approvalService;

    private ApprovalActionRequestDto request;

    @BeforeEach
    void setUp() {

        request = new ApprovalActionRequestDto();

        request.setManagerId(501L);
        request.setComments("Approved by manager");
    }

    @Test
    void approvePurchase_success() {

        Long purchaseId = 1L;

        when(approvalRepository.findByPurchaseId(purchaseId))
                .thenReturn(Optional.empty());

        Approval savedApproval = new Approval();

        savedApproval.setId(10L);
        savedApproval.setPurchaseId(purchaseId);
        savedApproval.setManagerId(501L);
        savedApproval.setStatus("APPROVED");
        savedApproval.setComments("Approved by manager");
        savedApproval.setApprovedAt(LocalDateTime.now());
        savedApproval.setCreatedAt(LocalDateTime.now());
        savedApproval.setUpdatedAt(LocalDateTime.now());

        when(approvalRepository.save(any(Approval.class)))
                .thenReturn(savedApproval);

        ApprovalResponseDto response =
                approvalService.approvePurchase(
                        purchaseId,
                        request
                );

        assertNotNull(response);

        assertEquals(
                10L,
                response.getId()
        );

        assertEquals(
                purchaseId,
                response.getPurchaseId()
        );

        assertEquals(
                501L,
                response.getManagerId()
        );

        assertEquals(
                "APPROVED",
                response.getStatus()
        );

        assertEquals(
                "Approved by manager",
                response.getComments()
        );

        verify(
                purchaseServiceClient,
                times(1)
        ).updatePurchaseStatus(
                purchaseId,
                "APPROVED"
        );

        verify(
                approvalRepository,
                times(1)
        ).save(any(Approval.class));
    }

    @Test
    void rejectPurchase_success() {

        Long purchaseId = 2L;

        when(approvalRepository.findByPurchaseId(purchaseId))
                .thenReturn(Optional.empty());

        Approval savedApproval = new Approval();

        savedApproval.setId(20L);
        savedApproval.setPurchaseId(purchaseId);
        savedApproval.setManagerId(501L);
        savedApproval.setStatus("REJECTED");
        savedApproval.setComments("Budget not approved");
        savedApproval.setApprovedAt(null);
        savedApproval.setCreatedAt(LocalDateTime.now());
        savedApproval.setUpdatedAt(LocalDateTime.now());

        request.setComments("Budget not approved");

        when(approvalRepository.save(any(Approval.class)))
                .thenReturn(savedApproval);

        ApprovalResponseDto response =
                approvalService.rejectPurchase(
                        purchaseId,
                        request
                );

        assertNotNull(response);

        assertEquals(
                20L,
                response.getId()
        );

        assertEquals(
                purchaseId,
                response.getPurchaseId()
        );

        assertEquals(
                501L,
                response.getManagerId()
        );

        assertEquals(
                "REJECTED",
                response.getStatus()
        );

        assertEquals(
                "Budget not approved",
                response.getComments()
        );

        verify(
                purchaseServiceClient,
                times(1)
        ).updatePurchaseStatus(
                purchaseId,
                "REJECTED"
        );

        verify(
                approvalRepository,
                times(1)
        ).save(any(Approval.class));
    }


    @Test
    void approvePurchase_alreadyApproved() {

        Long purchaseId = 3L;

        Approval existingApproval =
                createApproval(
                        30L,
                        purchaseId,
                        "APPROVED"
                );

        when(
                approvalRepository.findByPurchaseId(purchaseId)
        ).thenReturn(
                Optional.of(existingApproval)
        );

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> approvalService.approvePurchase(
                                purchaseId,
                                request
                        )
                );

        assertEquals(
                "Purchase is already approved",
                exception.getMessage()
        );

        verify(
                purchaseServiceClient,
                never()
        ).updatePurchaseStatus(
                any(Long.class),
                any(String.class)
        );

        verify(
                approvalRepository,
                never()
        ).save(any(Approval.class));
    }

    @Test
    void approvePurchase_alreadyRejected() {

        Long purchaseId = 4L;

        Approval existingApproval =
                createApproval(
                        40L,
                        purchaseId,
                        "REJECTED"
                );

        when(
                approvalRepository.findByPurchaseId(purchaseId)
        ).thenReturn(
                Optional.of(existingApproval)
        );

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> approvalService.approvePurchase(
                                purchaseId,
                                request
                        )
                );

        assertEquals(
                "Purchase is already rejected",
                exception.getMessage()
        );

        verify(
                purchaseServiceClient,
                never()
        ).updatePurchaseStatus(
                any(Long.class),
                any(String.class)
        );

        verify(
                approvalRepository,
                never()
        ).save(any(Approval.class));
    }

    @Test
    void rejectPurchase_alreadyApproved() {

        Long purchaseId = 5L;

        Approval existingApproval =
                createApproval(
                        50L,
                        purchaseId,
                        "APPROVED"
                );

        when(
                approvalRepository.findByPurchaseId(purchaseId)
        ).thenReturn(
                Optional.of(existingApproval)
        );

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> approvalService.rejectPurchase(
                                purchaseId,
                                request
                        )
                );

        assertEquals(
                "Purchase is already approved",
                exception.getMessage()
        );

        verify(
                purchaseServiceClient,
                never()
        ).updatePurchaseStatus(
                any(Long.class),
                any(String.class)
        );

        verify(
                approvalRepository,
                never()
        ).save(any(Approval.class));
    }

    @Test
    void rejectPurchase_alreadyRejected() {

        Long purchaseId = 6L;

        Approval existingApproval =
                createApproval(
                        60L,
                        purchaseId,
                        "REJECTED"
                );

        when(
                approvalRepository.findByPurchaseId(purchaseId)
        ).thenReturn(
                Optional.of(existingApproval)
        );

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> approvalService.rejectPurchase(
                                purchaseId,
                                request
                        )
                );

        assertEquals(
                "Purchase is already rejected",
                exception.getMessage()
        );

        verify(
                purchaseServiceClient,
                never()
        ).updatePurchaseStatus(
                any(Long.class),
                any(String.class)
        );

        verify(
                approvalRepository,
                never()
        ).save(any(Approval.class));
    }

    private Approval createApproval(
            Long id,
            Long purchaseId,
            String status) {

        Approval approval = new Approval();

        approval.setId(id);
        approval.setPurchaseId(purchaseId);
        approval.setManagerId(501L);
        approval.setStatus(status);
        approval.setComments("Test comment");
        approval.setCreatedAt(LocalDateTime.now());
        approval.setUpdatedAt(LocalDateTime.now());

        if ("APPROVED".equals(status)) {
            approval.setApprovedAt(LocalDateTime.now());
        }

        return approval;
    }
}