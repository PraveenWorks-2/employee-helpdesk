package com.example.approval_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "approvals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approval {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "purchase_id",nullable = false,unique = true)
	private Long purchaseId;

	@Column(name = "manager_id",nullable = false)
	private Long managerId;
	
	@Column(name = "status",nullable = false,length = 30)
	private String status;
	
	@Column(name = "comments",length = 1000)
	private String comments;
	
	@Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;
	
}
