package com.example.employee_helpdesk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

	private Long id;
	private String employeeCode;
	private String name;
	private String email;
	private String phone;
	private String department;
	private String designation;
	private String status;
	
}
