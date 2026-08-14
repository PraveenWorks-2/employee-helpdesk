package com.example.employee_helpdesk.entity;

import com.example.employee_helpdesk.enums.Statuses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_code", nullable = false, unique = true, length = 50)
    private String assetCode;

    @Column(name = "name", nullable = false,  length = 50)
    private String name;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "serial_number", nullable = false, unique = true, length = 50)
    private String serialNumber;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Statuses status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;


}
