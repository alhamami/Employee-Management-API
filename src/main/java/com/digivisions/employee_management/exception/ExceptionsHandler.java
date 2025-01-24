package com.digivisions.employee_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, String>> handleInvalidInputException(InvalidInputException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(DatabaseException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
