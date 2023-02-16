package com.example.employeecrudapplication.validator;

import com.example.employeecrudapplication.exception.EmployeeValidationException;
import com.example.employeecrudapplication.model.EmployeeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator {

    public void validateEmail(String email) {
        if (
                StringUtils.isBlank(email)
                || (!email.contains("@"))
                || (!email.contains("."))
        ) {
            throw new EmployeeValidationException("Employee Email is not present in the request");
        }
    }

    public void validateFields(EmployeeDto employee) {
        if (employee == null) {
            throw new EmployeeValidationException("Null Pointer Exception! Employee request is null");
        }
        if (StringUtils.isBlank(employee.getFirstName())) {
            throw new EmployeeValidationException("Employee First Name is not present in the request");
        }
        if (StringUtils.isBlank(employee.getLastName())) {
            throw new EmployeeValidationException("Employee Last Name is not present in the request");
        }
        validateEmail(employee.getEmail());
    }

    public void validToCreate(EmployeeDto employee) {
        validateFields(employee);
    }

    public void validToUpdate(EmployeeDto employee) {
        validateFields(employee);
    }

//    public void validForUpdate() {
//
//    }
}
