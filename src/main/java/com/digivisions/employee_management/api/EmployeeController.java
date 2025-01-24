package com.digivisions.employee_management.api;
import com.digivisions.employee_management.exception.InvalidInputException;
import com.digivisions.employee_management.DTO.EmployeeDTO;
import com.digivisions.employee_management.DTO.EmployeeResponseDTO;
import com.digivisions.employee_management.exception.EmployeeNotFoundException;
import com.digivisions.employee_management.service.EmployeeMapper;
import com.digivisions.employee_management.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);



    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> listAllEmployees() {

        logger.info("EmployeeController: List All Employees received");
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();

        logger.info("Employees successfully retrieved: "+ employees);
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {

        logger.info("EmployeeController: Get Employee By Id received to get Employee with ID: "+ id);

        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeById(id);
        if (employeeResponseDTO != null) {

            logger.info("Employee successfully retrieved: "+ employeeResponseDTO);
            return new ResponseEntity<>(employeeResponseDTO, HttpStatus.OK);

        }else{
            logger.info("Employee not found: "+ employeeResponseDTO);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {

        logger.info("EmployeeController: Create Employee received: "+ employeeDTO);
        try {

            EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeDTO);

            logger.info("Employee successfully created: "+ employeeResponseDTO);
            return new ResponseEntity<>(employeeResponseDTO, HttpStatus.CREATED);

        } catch (InvalidInputException e) {

            logger.error("Invalid input can't create employee: "+ employeeDTO);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @Validated @RequestBody EmployeeDTO employeeDTO) {

        logger.info("EmployeeController: Update Employee received: "+ employeeDTO+" with id: "+ id);
        EmployeeResponseDTO  updatedEmployeeResponseDTO = employeeService.updateEmployee(id, employeeDTO);

        logger.info("Employee successfully updated: "+ updatedEmployeeResponseDTO);

        return new ResponseEntity<>(updatedEmployeeResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        logger.info("EmployeeController: Delete Employee with id "+id+" received");

        try {
            employeeService.deleteEmployee(id);
            logger.info("Employee successfully deleted: "+ id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (EmployeeNotFoundException e) {
            logger.error("Employee not found: "+ id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
