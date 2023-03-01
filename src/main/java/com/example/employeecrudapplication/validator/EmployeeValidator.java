package com.example.employeecrudapplication.validator;

import com.example.employeecrudapplication.exception.EmployeeValidationException;
import com.example.employeecrudapplication.model.EmployeeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmployeeValidator {

    private boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public void validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new EmployeeValidationException("You haven't provided an Email");
        }
        String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
                if (!(patternMatches(email, emailRegexPattern))) {
            throw new EmployeeValidationException("The Email you provided is not properly formatted");
        }
    }

    public void validateFields(EmployeeDto employee) {
        if (employee == null) {
            throw new EmployeeValidationException("You haven't provided the Employee data" );
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
}
