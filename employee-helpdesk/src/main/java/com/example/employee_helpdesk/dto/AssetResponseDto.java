package com.example.employee_helpdesk.dto;


import com.example.employee_helpdesk.enums.Statuses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDto {

    private Long id;

    private String assetCode;

    private String name;

    private String category;

    private String serialNumber;

    private Statuses status;

    private LocalDate purchaseDate;

    private LocalDate createdAt;

    private  LocalDate updatedAt;
}
