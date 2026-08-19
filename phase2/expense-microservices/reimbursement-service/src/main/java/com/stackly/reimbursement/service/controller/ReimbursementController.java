package com.stackly.reimbursement.service.controller;

import com.stackly.reimbursement.service.dto.ReimbursementResponseDto;
import com.stackly.reimbursement.service.service.ReimbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reimbursements")
@RequiredArgsConstructor
public class ReimbursementController {
    public final ReimbursementService reimbursementService;

    @PostMapping("/{expenseId}/approve")
    public ResponseEntity<ReimbursementResponseDto> approveReimbursement
            (@PathVariable Long expenseId){
        ReimbursementResponseDto updatedReimbursement = reimbursementService.
                approveReimbursement(expenseId);
        return ResponseEntity.ok(updatedReimbursement);
    }
    @PostMapping("/{expenseId}/reject")
    public ResponseEntity<ReimbursementResponseDto> rejectReimbursement
            (@PathVariable Long expenseId){
        ReimbursementResponseDto updatedReimbursement = reimbursementService.
                rejectReimbursement(expenseId);
        return ResponseEntity.ok(updatedReimbursement);
    }
    @PostMapping("/{expenseId}/pay")
    public ResponseEntity<ReimbursementResponseDto> payReimbursement
            (@PathVariable Long expenseId){
        ReimbursementResponseDto updatedReimbursement = reimbursementService.
                payReimbursement(expenseId);
        return ResponseEntity.ok(updatedReimbursement);
    }
}
