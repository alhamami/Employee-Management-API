package com.digivisions.employee_management.controller;
import com.digivisions.employee_management.exception.InvalidInputException;
import com.digivisions.employee_management.DTO.EmployeeDTO;
import com.digivisions.employee_management.DTO.EmployeeResponseDTO;
import com.digivisions.employee_management.entity.Employee;
import com.digivisions.employee_management.exception.EmployeeNotFoundException;
import com.digivisions.employee_management.repository.EmployeeRepository;
import com.digivisions.employee_management.service.EmployeeMapper;
import com.digivisions.employee_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper employeeMapper;


    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> listAllEmployees() {

        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {

        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeById(id);
        if (employeeResponseDTO != null) {
            return new ResponseEntity<>(employeeResponseDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {

        try {

            EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeDTO);

            return new ResponseEntity<>(employeeResponseDTO, HttpStatus.CREATED);

        } catch (InvalidInputException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @Validated @RequestBody EmployeeDTO employeeDTO) {

        return new ResponseEntity<>(employeeService.updateEmployee(id, employeeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (EmployeeNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
