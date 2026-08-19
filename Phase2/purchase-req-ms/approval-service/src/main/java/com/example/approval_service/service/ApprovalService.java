package com.example.approval_service.service;

import com.example.approval_service.dto.ApprovalActionRequestDto;
import com.example.approval_service.dto.ApprovalResponseDto;

public interface ApprovalService {
	
	ApprovalResponseDto approvePurchase(Long purchaseId,
			ApprovalActionRequestDto request);
	ApprovalResponseDto rejectPurchase(Long purchaseId,
			ApprovalActionRequestDto request);

}
