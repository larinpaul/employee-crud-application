package com.example.employeecrudapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;

//    public ErrorResponse(String message) {
//        this.message = message;
//    }

//    public String getMessage() {
//        return message;
//    }

}
