package com.stackly.expense.service.entity;

import com.stackly.expense.service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table (name = "expense")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "expense_type", nullable = false)
    private String expenseType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "submitted_at")
    private LocalDate submittedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Expense(@NotBlank(message = "Employee name is required") String employeeName,
                   @NotBlank(message = "Expense type is required") String expenseType,
                   @NotBlank(message = "Description is required") String description,
                   @NotNull(message = "Amount is required") @DecimalMin(value = "0.01",
                           message = "Amount must be greater than zero") BigDecimal amount){
        this.employeeName = employeeName;
        this.expenseType = expenseType;
        this.description = description;
        this.amount = amount;
    }

}
