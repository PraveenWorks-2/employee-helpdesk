package com.example.employee_helpdesk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_helpdesk.dto.EmployeeRequest;
import com.example.employee_helpdesk.dto.EmployeeResponse;
import com.example.employee_helpdesk.entity.Employee;
import com.example.employee_helpdesk.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
	
	private final EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeResponse createEmployee(EmployeeRequest request) {
		// TODO Auto-generated method stub
		if(employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
			throw new RuntimeException("Employee code already exists");
		}
		if(employeeRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Employee email already exists");
		}
		
		Employee employee = new Employee();
		employee.setEmployeeCode(request.getEmployeeCode());
		employee.setName(request.getName());
		employee.setEmail(request.getEmail());
		employee.setPhone(request.getPhone());
		employee.setDepartment(request.getDepartment());
		employee.setDesignation(request.getDesignation());
		employee.setStatus(request.getStatus());
		
		Employee savedEmployee = employeeRepository.save(employee);
		return convertToResponse(savedEmployee);
	}

	@Override
	public List<EmployeeResponse> getAllEmployees() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll().stream()
				.map(this::convertToResponse).toList();
	}

	@Override
	public EmployeeResponse getEmployeeById(Long id) {
		// TODO Auto-generated method stub
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		return convertToResponse(employee);
	}

	@Override
	public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
		// TODO Auto-generated method stub
		Employee employee = employeeRepository.findById(id).orElseThrow(()
				-> new RuntimeException("Employee not found"));
		employee.setEmployeeCode(request.getEmployeeCode());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setStatus(request.getStatus());

        Employee updatedEmployee = employeeRepository.save(employee);

        return convertToResponse(updatedEmployee);
	}

	@Override
	public void deleteEmployee(Long id) {
		// TODO Auto-generated method stub
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		
		employeeRepository.delete(employee);
		
	}
	
	private EmployeeResponse convertToResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getStatus()
        );
    }

}
