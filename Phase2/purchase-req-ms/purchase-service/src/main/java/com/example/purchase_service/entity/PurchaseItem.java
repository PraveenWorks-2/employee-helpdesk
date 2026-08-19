package com.example.purchase_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItem {

		@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "item_name", nullable = false, length = 100)
	private String itemName;
	
	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
	private BigDecimal unitPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_request_id", nullable = false)
	private PurchaseRequest purchaseRequest;
	
}
