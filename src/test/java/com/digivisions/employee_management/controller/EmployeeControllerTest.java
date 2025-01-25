package com.digivisions.employee_management.controller;

import com.digivisions.employee_management.DTO.EmployeeDTO;
import com.digivisions.employee_management.DTO.EmployeeResponseDTO;
import com.digivisions.employee_management.exception.EmployeeNotFoundException;
import com.digivisions.employee_management.exception.InvalidInputException;
import com.digivisions.employee_management.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAllEmployees() {

        EmployeeResponseDTO firstEmployee = new EmployeeResponseDTO();
        EmployeeResponseDTO secondEmployee = new EmployeeResponseDTO();
        List<EmployeeResponseDTO> employees = new ArrayList<>();
        employees.add(firstEmployee);
        employees.add(secondEmployee);

        when(employeeService.getAllEmployees()).thenReturn(employees);
        ResponseEntity<List<EmployeeResponseDTO>> listAllEmployee = employeeController.listAllEmployees();

        assertNotNull(listAllEmployee);
        assertEquals(200, listAllEmployee.getStatusCodeValue());
        assertEquals(employees.size(), listAllEmployee.getBody().size());
    }

    @Test
    public void testGetEmployeeById() {

        EmployeeResponseDTO newEmployee = new EmployeeResponseDTO();
        newEmployee.setId(1L);

        when(employeeService.getEmployeeById(1L)).thenReturn(newEmployee);
        ResponseEntity<EmployeeResponseDTO> employee = employeeController.getEmployeeById(1L);

        assertNotNull(employee);
        assertEquals(200, employee.getStatusCodeValue());
        assertEquals(newEmployee.getId(), employee.getBody().getId());
    }


    @Test
    public void testGetEmployeeByIdNotFound() {

        when(employeeService.getEmployeeById(3L)).thenReturn(null);
        ResponseEntity<EmployeeResponseDTO> employee = employeeController.getEmployeeById(5L);

        assertNotNull(employee);
        assertEquals(404, employee.getStatusCodeValue());
    }


    @Test
    public void testCreateEmployee() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("jalhamami@outlook.com");

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setEmail("jalhamami@outlook.com");

        when(employeeService.createEmployee(employeeDTO)).thenReturn(employeeResponseDTO);
        ResponseEntity<EmployeeResponseDTO> employee = employeeController.createEmployee(employeeDTO);

        assertNotNull(employee);
        assertEquals(201, employee.getStatusCodeValue());
        assertEquals(employeeResponseDTO.getEmail(), employee.getBody().getEmail());
    }


    @Test
    public void testCreateEmployeeWithInvalidEmail() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("jalhamami@outtlook.com");

        when(employeeService.createEmployee(employeeDTO)).thenThrow( new InvalidInputException("Invalid Email"));
        ResponseEntity<EmployeeResponseDTO> employee = employeeController.createEmployee(employeeDTO);


        assertNotNull(employee);
        assertEquals(400, employee.getStatusCodeValue());
    }

    @Test
    public void testUpdateEmployee() {

        EmployeeDTO updateEmployeeDTO = new EmployeeDTO();
        updateEmployeeDTO.setFirstName("Jalal");

        EmployeeResponseDTO  employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setFirstName("Jalal");

        when(employeeService.updateEmployee(1L, updateEmployeeDTO)).thenReturn(employeeResponseDTO);
        ResponseEntity<EmployeeResponseDTO> employee = employeeController.updateEmployee(1L, updateEmployeeDTO);


        assertNotNull(employee);
        assertEquals(200, employee.getStatusCodeValue());
        assertEquals(updateEmployeeDTO.getFirstName(), employee.getBody().getFirstName());
    }


    @Test
    public void testDeleteEmployee() {

        doNothing().when(employeeService).deleteEmployee(1L);
        ResponseEntity<Void> employee = employeeController.deleteEmployee(1L);

        assertNotNull(employee);
        assertEquals(204, employee.getStatusCodeValue());
    }


    @Test
    public void testDeleteEmployeeNotExist() {

        doThrow(new EmployeeNotFoundException("Employee not found")).when(employeeService).deleteEmployee(1L);
        ResponseEntity<Void> employee = employeeController.deleteEmployee(1L);

        assertNotNull(employee);
        assertEquals(404, employee.getStatusCodeValue());
    }

}
