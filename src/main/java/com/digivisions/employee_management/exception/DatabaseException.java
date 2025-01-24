package com.digivisions.employee_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error occurred in Database")
public class DatabaseException extends RuntimeException{

    public DatabaseException(String message) {
        super(message);
    }
}
