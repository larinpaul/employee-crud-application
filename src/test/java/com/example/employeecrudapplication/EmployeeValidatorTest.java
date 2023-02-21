//package com.example.employeecrudapplication;
//
//import com.example.employeecrudapplication.exception.EmployeeValidationException;
//import com.example.employeecrudapplication.model.dto.EmployeeDto;
//import com.example.employeecrudapplication.validator.EmployeeValidator;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringJUnitConfig
//class EmployeeValidatorTest {
//
//    // Все тестовые классы и методы - ни private, ни public, ни protected
//    @InjectMocks
//    private EmployeeValidator employeeValidator;
//
//    // Testing validateEmail()
//    @Test
//    @DisplayName("Should accept valid email")
//    void testValidateEmail1() {
//        String emailExpected1 = "gooduser@domain.com";
//        assertDoesNotThrow(
//                () -> employeeValidator.validateEmail(emailExpected1)
//        );
//
//        // слева - ожидаемые значения, справа - то, что получил
//
////        assertTrue(employeeValidator.validateEmail(emailExpected1));
////        String emailExpected2 = "good.user@domain.com";
////        String emailExpected3 = "good-user@domain.com";
////        String emailExpected4 = "good_user@domain.com";
////        String emailHasSymbols = "me!@#$%^&*()@.com"; // Maybe use an ArrayList
////        String emailAllUpperCase = "BIGLETTERS@mail.com";
//
//        // Valid // Happy Case
//        // Empty // Unhappy
//        // Incorrect // Unhappy
//    }
//
//    @Test
//    @DisplayName("Should reject empty email string")
//    void shouldRejectEmptyEmailString() {
//        String emailEmpty = "";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailEmpty)
//        );
//        assertEquals("You haven't provided an Email", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject empty email string space")
//    void shouldRejectBlankEmailString() {
//        String emailEmpty = " ";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailEmpty)
//        );
//        assertEquals("You haven't provided an Email", exception.getMessage());
//    }
//
//
//    @Test
//    @DisplayName("Should reject empty email null")
//    void shouldRejectNullEmail() {
//        String emailEmpty = null;
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailEmpty)
//        );
//        assertEquals("You haven't provided an Email", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email with no domain")
//    void testValidateEmail3_1() {
//        String emailNoDomain = "nocomdomain@.";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailNoDomain)
//        );
//        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email with no @")
//    void testValidateEmail3_2() {
//        String emailNoAt = "noAt.com";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailNoAt)
//        );
//        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email with no service")
//    void testValidateEmail3_3() {
//        String emailNoService = "noservice@.com";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailNoService)
//        );
//        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email that starts with a dot")
//    void testValidateEmail3_4() {
//        String emailBeginsWithDot = ".beginswithdot@domain.com";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailBeginsWithDot)
//        );
//        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email with more than one consequent dots")
//    void testValidateEmail3_5() {
//        String emailManyDots = "manydots@mail..com";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailManyDots)
//        );
//        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email with no dots")
//    void testValidateEmail3_6() {
//        String emailNoDots = "nodots@mailcom";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailNoDots)
//        );
//        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email with no name")
//    void testValidateEmail3_7() {
//        String emailNoName = "@mail.com";
//        EmployeeValidationException exception = assertThrows(
//                EmployeeValidationException.class,
//                () -> employeeValidator.validateEmail(emailNoName)
//        );
//        assertEquals("The Email you provided is not properly formatted", exception.getMessage());
//    }
//
//    // Testing validateFields()
//
//    @Test
//    @DisplayName("Should throw EmployeeValidationException if employee is null")
//    void testValidateFieldsNullEmployee() {
//        Assertions.assertThrows(EmployeeValidationException.class, () -> {
//            employeeValidator.validateFields(null);
//        });
//    }
//
//    @Test
//    @DisplayName("Should throw EmployeeValidationException if first name is blank ")
//    void testValidateFieldsBlankFirstName() {
//
//        validEmployee.setFirstName("");
//        assertThrows(EmployeeValidationException.class, () -> employeeValidator.validateFields(validEmployee));
//    }
//
//    @Test
//    @DisplayName("Should throw EmployeeValidationException if last name is blank")
//    void testValidateFieldsBlankLastName() {
//
//        validEmployee.setLastName("");
//        assertThrows(EmployeeValidationException.class, () -> employeeValidator.validateFields(validEmployee));
//    }
//
//    @Test
//    @DisplayName("Should not throw an Exception if given a valid employee")
//    void testValidateFieldsValidEmployee() {
//        assertDoesNotThrow(() -> employeeValidator.validateFields(validEmployee));
//    }
//
//    @Test
//    @DisplayName("Should call validateEmail if given a valid employee")
//    void testValidateFieldsEmailOfValidEmployee() {
//        EmployeeValidator spyEmployeeValidator = spy(employeeValidator);
//        spyEmployeeValidator.validateFields(validEmployee);
//        verify(spyEmployeeValidator, times(1)).validateEmail(validEmployee.getEmail());
//    }
//
//    @Test
//    @DisplayName("Should reject empty first name")
//    void testValidateFields2() {
//
//    }
//
//    @Test
//    @DisplayName("Should reject empty last name")
//    void testValidateFields3() {
//
//    }
//
//    @Test
//    @DisplayName("Should accept valid email")
//    void testValidateFields4() {
//
//    }
//
//    @Test
//    @DisplayName("Should reject empty email")
//    void testValidateFields5() {
//
//    }
//
//    @Test
//    @DisplayName("Should reject invalid email")
//    void testValidateFields6() {
//
//    }
//
//    // Mock unit tests for EmployeeService
//    @Test
//    @DisplayName("Should reject invalid email")
//    void testValidateEmployeeService() {
//
//    }
//    // Будем мокать все филды из этого сервиса,
//    // А также стабать
//    // Stub: a dummy piece of code that lets the test run, but you don't care what happens to it. Substitutes for real working code.
//    // E.g. Retrieve data
//    // Mock: a dummy piece of code that you verify is called correctly as part of the test.
//    // E.g. Send an email
//
//
//    // Мокает репозиторий.
//    // Он не делает ничего, возвращает null.
//    // Например это полезно чтобы проверить *только* как работает метод
//    // Например метод возвращает цену всех товаров, (колво*цена)
//    // МОК - "Объект Шредингера", на всё возвращает null
//    // MOCK ВООБЩЕ ВСЕГДА ВОЗВРАЩАЕТ NULL
//
//    // Юнит тест: мокаем репо с ценами, валютами и т д
//    // А в юнит-тесте уже работашь с этими данными.
//    // Например перечисляется цена на все товары
//    // Например цена 2 доллара, а пользователь запросил 4 штуки,
//    // По любому тут должно вернуться 8 долларов
//    // Цена в данном случае должна вернуться через СТАБ,
//    // А уже произведение = реальное поведение метода
//    // STUB ВОЗВРАЩАЕТ ТРЕБУЕМЫЙ ОБЪЕКТ, КОТОРЫЙ ТЫ ЕМУ УКАЗАЛ В ТЕСТЕ, А ПРИ НЕПОДХОДЯЩИХ ДАННЫХ, ТО NULL
//
//    // Если "лень" реализовывать, то СТАБ (указать явно как  метод должен себя вести)
//    // СТАБ - Какие данные должны вернуться при каких-то входных
//    // А если хочешь использовать свои данные, то СПАЙ
//    // SPY ВОЗВРАЩАЕТ (либо застабить все методы, все методы рабочие, но можно переопределить)
//    // Это как Mock на стероидах
//    // Но этим злоупотребялять не надо, потому что по сути получается заинжекчивание не мока, а реального объекта
//
//    // У нас в ВАЛИДАТОРЕ mock, spy, stub не получится использовать,
//    // Надо это делать в СЕРВИСЕ
//
//    // Mocking and stubbing for each field of the validateFields
//    @Test
//    @DisplayName("Mocking and stubbing for each field of the validateFields method")
//    void testValidateFieldsMockStub() {
//        // Trying to create a mock
//        EmployeeDto validEmployee = mock(EmployeeDto.class);
//
//        assertEquals(validEmployee, ); // Сравнить с тем, что есть в ДБ?
//        // Сравнивать, это когда метод возвращает какую-то информацию, а в данном случае метод void, поэтому
//
//        // п
//
//
//
//
//
//
//        /// Generated by AI
//        // Mock the dependencies
//        validEmployee = new EmployeeDto("Mocker", "Stubly", "mocker.stubly@mail.com");
//        validEmployee = mock(EmployeeDto("Mocker", "Stubly", "mocker.stubly@mail.com"));
//        String validEmail = validEmployee.getEmail();
//        StringUtils stringUtils = mock(StringUtils.class);
//        // Stub the behavior of the StringUtils methods
//        when(stringUtils.isBlank(validEmployee.getFirstName())).thenReturn(false);
//        when(stringUtils.isBlank(validEmployee.getLastName())).thenReturn(false);
//
//    }
//
//}
