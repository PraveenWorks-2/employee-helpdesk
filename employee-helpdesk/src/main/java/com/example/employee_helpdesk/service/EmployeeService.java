package com.example.employee_helpdesk.service;

import java.util.List;

import com.example.employee_helpdesk.dto.EmployeeRequest;
import com.example.employee_helpdesk.dto.EmployeeResponse;

public interface EmployeeService {

	EmployeeResponse createEmployee(EmployeeRequest request);
	List<EmployeeResponse> getAllEmployees();
	EmployeeResponse getEmployeeById(Long id);
	EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
	void deleteEmployee(Long id);
	
}
