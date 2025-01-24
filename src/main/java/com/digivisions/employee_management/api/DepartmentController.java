package com.digivisions.employee_management.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private static final List<String> DEPARTMENTS = List.of("Administrative", "Marketing", "IT", "Finance", "Purchasing", "Legal");

    @GetMapping("/department")
    public ResponseEntity<Boolean> validateDepartment(@RequestParam String department) {

        logger.info("DepartmentController: Validate Department received with department: " + department);

        boolean isValidDepartment = DEPARTMENTS.contains(department);
        logger.info("Department validated" );
        return ResponseEntity.ok(isValidDepartment);
    }
}
