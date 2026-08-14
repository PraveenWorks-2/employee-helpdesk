package com.example.employee_helpdesk.dto;

import com.example.employee_helpdesk.enums.Statuses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequestDto {

    private Long id;

    @NotBlank(message = "Asset code is required")
    @Size(max = 50, message = "Asset code must not exceed 50 characters")
    private String assetCode;

    @NotBlank(message = "Asset name is required")
    @Size(max = 50, message = "Asset name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;

    @NotBlank(message = "Serial number is required")
    @Size(max = 50, message = "Serial number must not exceed 50 characters")
    private String serialNumber;

    private Statuses status;

    private LocalDate purchaseDate;
}