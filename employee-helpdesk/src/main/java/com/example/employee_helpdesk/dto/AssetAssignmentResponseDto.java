package com.example.employee_helpdesk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetAssignmentResponseDto {

    private Long id;

    private Long assetId;

    private String assetCode;

    private Long employeeId;

    private LocalDateTime assignedAt;

    private LocalDateTime returnedAt;

    private Boolean active;
}