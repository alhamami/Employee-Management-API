package com.digivisions.employee_management.service;

import com.digivisions.employee_management.DTO.EmployeeDTO;
import com.digivisions.employee_management.DTO.EmployeeResponseDTO;
import com.digivisions.employee_management.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeMapper.class);


    public Employee convertToEmployee(EmployeeDTO EmployeeDTO) {

        logger.info("EmployeeMapper: converting DTO to Employee ");

        Employee employee = new Employee();
        employee.setFirstName(EmployeeDTO.getFirstName());
        employee.setLastName(EmployeeDTO.getLastName());
        employee.setEmail(EmployeeDTO.getEmail());
        employee.setDepartment(EmployeeDTO.getDepartment());
        employee.setSalary(EmployeeDTO.getSalary());

        logger.info("Successfully converted DTO to Employee");

        return employee;
    }

    public EmployeeResponseDTO convertToDTO(Employee employee) {

        logger.info("EmployeeMapper: converting Employee to DTO");

        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDepartment(employee.getDepartment());
        employeeDTO.setSalary(employee.getSalary());

        logger.info("Successfully converted Employee to DTO");

        return employeeDTO;
    }

    public void updateEmployee(EmployeeDTO dto, Employee employee) {

        logger.info("EmployeeMapper: updating Employee");

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());

        logger.info("Successfully updated Employee");

    }
}
