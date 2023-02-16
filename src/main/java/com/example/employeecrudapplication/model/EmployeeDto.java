package com.example.employeecrudapplication.model;

import lombok.Data;

@Data
public class EmployeeDto { // With DTO we are limiting the fields number that a user can use

    private String firstName;

    private String lastName;

    private String email;
}
