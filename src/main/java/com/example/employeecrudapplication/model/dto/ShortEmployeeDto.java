package com.example.employeecrudapplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortEmployeeDto {

    private String firstName;

    private String lastName;

    private String email;
}
