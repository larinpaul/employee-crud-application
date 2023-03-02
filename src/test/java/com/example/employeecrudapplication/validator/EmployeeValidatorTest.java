package com.example.employeecrudapplication.validator;

import com.example.employeecrudapplication.exception.EmployeeValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class EmployeeValidatorTest {

    @InjectMocks
    private EmployeeValidator employeeValidator;

    // Testing validateEmail()
    @Test
    @DisplayName("Should accept valid email")
    void testValidateEmail1() {
        String emailExpected1 = "gooduser@domain.com";
        assertDoesNotThrow(
                () -> employeeValidator.validateEmail(emailExpected1)
        );
    }

    @Test
    @DisplayName("Should reject empty email string")
    void shouldRejectEmptyEmailString() {
        String emailEmpty = "";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailEmpty)
        );
        assertEquals("You haven't provided an Email", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject empty email string space")
    void shouldRejectBlankEmailString() {
        String emailEmpty = " ";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailEmpty)
        );
        assertEquals("You haven't provided an Email", exception.getMessage());
    }


    @Test
    @DisplayName("Should reject empty email null")
    void shouldRejectNullEmail() {
        String emailEmpty = null;
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailEmpty)
        );
        assertEquals("You haven't provided an Email", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject invalid email with no domain")
    void testValidateEmail3_1() {
        String emailNoDomain = "nocomdomain@.";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailNoDomain)
        );
        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject invalid email with no @")
    void testValidateEmail3_2() {
        String emailNoAt = "noAt.com";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailNoAt)
        );
        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject invalid email with no service")
    void testValidateEmail3_3() {
        String emailNoService = "noservice@.com";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailNoService)
        );
        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject invalid email that starts with a dot")
    void testValidateEmail3_4() {
        String emailBeginsWithDot = ".beginswithdot@domain.com";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailBeginsWithDot)
        );
        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject invalid email with more than one consequent dots")
    void testValidateEmail3_5() {
        String emailManyDots = "manydots@mail..com";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailManyDots)
        );
        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject invalid email with no dots")
    void testValidateEmail3_6() {
        String emailNoDots = "nodots@mailcom";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailNoDots)
        );
        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
    }

    @Test
    @DisplayName("Should reject invalid email with no name")
    void testValidateEmail3_7() {
        String emailNoName = "@mail.com";
        EmployeeValidationException exception = assertThrows(
                EmployeeValidationException.class,
                () -> employeeValidator.validateEmail(emailNoName)
        );
        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
    }


    @Test
    @DisplayName("Should throw EmployeeValidationException if employee is null")
    void testValidateFieldsNullEmployee() {
        Assertions.assertThrows(EmployeeValidationException.class, () -> employeeValidator.validateFields(null));
    }

}
