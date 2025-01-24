package com.digivisions.employee_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    private static final List<String> DEPARTMENTS = List.of("Administrative", "Marketing", "IT", "Finance", "Purchasing", "Legal");

    @GetMapping("/department")
    public ResponseEntity<Boolean> validateDepartment(@RequestParam String department) {

        boolean isValidDepartment = DEPARTMENTS.contains(department);

        return ResponseEntity.ok(isValidDepartment);
    }
}
