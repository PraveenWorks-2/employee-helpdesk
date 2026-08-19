package com.example.purchase_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "request_number",nullable = false, unique = true,length = 50)
	private String requestNumber;
	
	@Column(name = "employee_id",nullable = false)
	private Long employeeId;
	
	@Column(name="title",nullable = false, length = 200)
	private String title;
	
	@Column(name = "description",columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "total_amount",nullable = false,precision = 15, scale = 2)
	private BigDecimal totalAmount;
	
	@Column(name = "status",nullable = false,length = 30)
	private String status;
	
	@Column(name = "created_at",nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at",nullable = false)
	private LocalDateTime updatedAt;
	
	@OneToMany(
			mappedBy = "purchaseRequest",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<PurchaseItem> items = new ArrayList<>();
	
}
