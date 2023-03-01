package com.example.employeecrudapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmployeeValidationException extends RuntimeException {


    public EmployeeValidationException(String message) {
        super(message);

    }
}
