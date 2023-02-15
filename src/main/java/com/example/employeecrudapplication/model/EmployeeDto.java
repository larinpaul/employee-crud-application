package com.example.employeecrudapplication.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EmployeeDto { // Wi th DTO we are limiting the fields number that a user can use

    private String firstName;

    private String lastName;

    private String email;
}
