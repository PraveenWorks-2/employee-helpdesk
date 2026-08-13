package com.example.employee_helpdesk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "employee",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_employee_code", columnNames = "employee_code"),
				@UniqueConstraint(name = "uk_employee_email", columnNames = "email")
		}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "employee_code", nullable = false, unique = true, length = 50)
	private String employeeCode;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "email", nullable = false, unique = true, length = 150)
	private String email;
	
	@Column(name = "phone", nullable = false, length = 20)
	private String phone;
	
	@Column(name = "department", nullable = false, length = 100)
	private String department;
	
	@Column(name = "designation", nullable = false, length = 100)
	private String designation;
	
	@Column(name = "status", nullable = false, length = 20)
	private String status;
	
}
