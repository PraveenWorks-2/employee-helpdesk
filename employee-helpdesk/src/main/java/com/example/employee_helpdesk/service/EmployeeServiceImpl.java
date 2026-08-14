package com.example.employee_helpdesk.service;

import com.example.employee_helpdesk.dto.EmployeeRequest;
import com.example.employee_helpdesk.dto.EmployeeResponse;
import com.example.employee_helpdesk.entity.Employee;
import com.example.employee_helpdesk.exception.BadRequestException;
import com.example.employee_helpdesk.exception.ResourceNotFoundException;
import com.example.employee_helpdesk.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        log.info("Creating employee with code: {}",
                request.getEmployeeCode());

        if (employeeRepository.existsByEmployeeCode(
                request.getEmployeeCode())) {

            log.warn("Employee code already exists: {}",
                    request.getEmployeeCode());

            throw new BadRequestException(
                    "Employee code already exists");
        }

        if (employeeRepository.existsByEmail(request.getEmail())) {

            log.warn("Employee email already exists: {}",
                    request.getEmail());

            throw new BadRequestException(
                    "Employee email already exists");
        }

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setStatus(request.getStatus());

        Employee savedEmployee =
                employeeRepository.save(employee);

        log.info("Employee created successfully with id: {}",
                savedEmployee.getId());

        return convertToResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        log.info("Fetching all employees");

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        log.info("Fetching employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn("Employee not found with id: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        return convertToResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        log.info("Updating employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn("Employee not found with id: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setStatus(request.getStatus());

        Employee updatedEmployee =
                employeeRepository.save(employee);

        log.info("Employee updated successfully with id: {}",
                id);

        return convertToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        log.info("Deleting employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn("Employee not found with id: {}", id);

                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        employeeRepository.delete(employee);

        log.info("Employee deleted successfully with id: {}", id);
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