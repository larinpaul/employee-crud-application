package com.example.employeecrudapplication;

import com.example.employeecrudapplication.exception.EmployeeValidationException;
import com.example.employeecrudapplication.validator.EmployeeValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class EmployeeValidatorTest {

    // Все тестовые классы и методы - ни private, ни public, ни protected
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

        // слева - ожидаемые значения, справа - то, что получил

//        assertTrue(employeeValidator.validateEmail(emailExpected1));
//        String emailExpected2 = "good.user@domain.com";
//        String emailExpected3 = "good-user@domain.com";
//        String emailExpected4 = "good_user@domain.com";
//        String emailHasSymbols = "me!@#$%^&*()@.com"; // Maybe use an ArrayList
//        String emailAllUpperCase = "BIGLETTERS@mail.com";

        // Valid // Happy Case
        // Empty // Unhappy
        // Incorrect // Unhappy
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

    // Testing validateFields()
//    @Test
//    @DisplayName("Should reject empty employee")
//    void testValidateFields1() {
//        String employeeEmpty = null;
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(employeeEmpty)
//        );
//        assertEquals("You haven't provided the Employee data", exception.getMessage());
//    }
//    // RE-DO This test from scratch

    @Test
    @DisplayName("Should reject empty first name")
    void testValidateFields2() {

    }

    @Test
    @DisplayName("Should reject empty last name")
    void testValidateFields3() {

    }

    @Test
    @DisplayName("Should accept valid email")
    void testValidateFields4() {

    }

    @Test
    @DisplayName("Should reject empty email")
    void testValidateFields5() {

    }

    @Test
    @DisplayName("Should reject invalid email")
    void testValidateFields6() {

    }

    // Mock unit tests for EmployeeService
    @Test
    @DisplayName("Should reject invalid email")
    void testValidateEmployeeService() {

    }
    // Будем мокать все филды из этого сервиса,
    // А также стабать
    // Stub: a dummy piece of code that lets the test run, but you don't care what happens to it. Substitutes for real working code.
    // E.g. Retrieve data
    // Mock: a dummy piece of code that you verify is called correctly as part of the test.
    // E.g. Send an email

}
