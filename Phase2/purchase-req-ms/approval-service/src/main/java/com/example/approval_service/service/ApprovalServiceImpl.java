package com.example.approval_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.approval_service.client.PurchaseServiceClient;
import com.example.approval_service.dto.ApprovalActionRequestDto;
import com.example.approval_service.dto.ApprovalResponseDto;
import com.example.approval_service.entity.Approval;
import com.example.approval_service.repository.ApprovalRepository;

@Service
public class ApprovalServiceImpl
        implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final PurchaseServiceClient purchaseServiceClient;

    public ApprovalServiceImpl(
            ApprovalRepository approvalRepository, PurchaseServiceClient purchaseServiceClient) {

        this.approvalRepository =
                approvalRepository;
		this.purchaseServiceClient = purchaseServiceClient;
    }

    @Override
    @Transactional
    public ApprovalResponseDto approvePurchase(
            Long purchaseId,
            ApprovalActionRequestDto request) {

        Approval approval =
                approvalRepository
                        .findByPurchaseId(purchaseId)
                        .orElse(null);

        if (approval != null) {

            if ("APPROVED".equals(approval.getStatus())) {

                throw new IllegalStateException(
                        "Purchase is already approved"
                );
            }

            if ("REJECTED".equals(approval.getStatus())) {

                throw new IllegalStateException(
                        "Purchase is already rejected"
                );
            }

        } else {

            approval = new Approval();
            approval.setPurchaseId(purchaseId);
            approval.setCreatedAt( LocalDateTime.now());
        }
        
        purchaseServiceClient.updatePurchaseStatus(
                purchaseId,
                "APPROVED"
        );

        LocalDateTime now =
                LocalDateTime.now();

        approval.setManagerId(request.getManagerId());
        approval.setStatus("APPROVED");
        approval.setComments(request.getComments());

        approval.setApprovedAt(now);

        approval.setUpdatedAt(now);

        Approval saved =
                approvalRepository.save(approval);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ApprovalResponseDto rejectPurchase(
            Long purchaseId,
            ApprovalActionRequestDto request) {

        Approval approval =approvalRepository
        		.findByPurchaseId(purchaseId).orElse(null);

        if (approval != null) {

            if ("APPROVED".equals(approval.getStatus())) {
                throw new IllegalStateException(
                        "Purchase is already approved");
            }

            if ("REJECTED".equals(approval.getStatus())) {
                throw new IllegalStateException(
                        "Purchase is already rejected");
            }

        } else {
            approval = new Approval();
            approval.setPurchaseId(purchaseId);
            approval.setCreatedAt(LocalDateTime.now() );
        }
        purchaseServiceClient.updatePurchaseStatus(
                purchaseId,"REJECTED");

        LocalDateTime now = LocalDateTime.now();
        approval.setManagerId(request.getManagerId());
        approval.setStatus("REJECTED");
        approval.setComments(request.getComments());
        approval.setApprovedAt(null);
        approval.setUpdatedAt(now);

        Approval saved =approvalRepository.save(approval);

        return mapToResponse(saved);
    }

    private ApprovalResponseDto mapToResponse(
            Approval approval) {

        ApprovalResponseDto response =
                new ApprovalResponseDto();

        response.setId(
                approval.getId()
        );

        response.setPurchaseId(
                approval.getPurchaseId()
        );

        response.setManagerId(
                approval.getManagerId()
        );

        response.setStatus(
                approval.getStatus()
        );

        response.setComments(
                approval.getComments()
        );

        response.setApprovedAt(
                approval.getApprovedAt()
        );

        response.setCreatedAt(
                approval.getCreatedAt()
        );

        response.setUpdatedAt(
                approval.getUpdatedAt()
        );

        return response;
    }
}