package com.enterprise.budgetapproval.approval.dto.response;

import com.enterprise.budgetapproval.approval.entity.ApprovalRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalResponse {

    private Long id;
    private Long budgetId;
    private ApprovalRequest.ApprovalStatus status;
    private String remarks;
    private LocalDateTime requestedAt;
    private LocalDateTime decidedAt;
}