package com.example.employee_helpdesk.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetAssignmentRequestDto {

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be greater than zero")
    private Long employeeId;
}