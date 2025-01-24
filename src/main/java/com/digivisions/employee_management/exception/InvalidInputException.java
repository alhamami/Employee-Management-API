package com.digivisions.employee_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Input")
public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message) {
        super(message);
    }
}
