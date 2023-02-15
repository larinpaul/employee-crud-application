package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.exception.EmployeeValidationException;
import com.example.employeecrudapplication.model.Employee;
import com.example.employeecrudapplication.model.EmployeeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator {

    public void validToCreate(EmployeeDto employee) {
        if (employee == null) {
            throw new EmployeeValidationException("Employee data is not provided");
        }
        // EMAIL
        // etc
        // Пока что не будем трогать сложную валидацию,
        // МОжно пока проверить что филды НЕ ПУСТЫЕ,
        // Для этого из ApacheCommons StringUtils.isBlank
        // Подключишь ApacheCommons
        // На примере такого:
        // StringUtils.isBlank(employee.getFirstName())
        // isEmpty, isNotEmpty
        // в документации посмотреть чем отличается от обычного isBlank
        StringUtils.isBlank(employee.getFirstName())
                // Ддля всех полей валидацию и эксепшн

    }


    public boolean validateEmail(String email) {
        // Validate email (for example, check if it contains "@" and "." and is not empty)
        if (email == null || email.isBlank() || !email.contains("@") || !email.contains(".")) {
            return false;
        }
        return true;
    }

    // Add more validation methods for other fields as needed

}
