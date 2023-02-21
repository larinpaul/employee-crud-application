package com.example.employeecrudapplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto { // With DTO we are limiting the fields number that a user can use

    private String firstName;

    private String lastName;

    private String email;
}
