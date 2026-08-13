package com.example.employee_helpdesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequest {

	@NotBlank
	@Size(max = 50)
	private String employeeCode;

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Size(max = 150)
	private String email;

	@NotBlank
	@Size(max = 20)
	private String phone;

	@NotBlank
	@Size(max = 100)
	private String department;

	@NotBlank
	@Size(max = 100)
	private String designation;

	@NotBlank
	@Size(max = 20)
	private String status;
}
