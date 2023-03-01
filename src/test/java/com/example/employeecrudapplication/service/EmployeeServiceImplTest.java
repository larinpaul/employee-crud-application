package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.data.repository.EmployeeRepository;
import com.example.employeecrudapplication.exception.EmployeeValidationException;
import com.example.employeecrudapplication.mapper.EmployeeMapperImpl;
import com.example.employeecrudapplication.model.domain.Employee;
import com.example.employeecrudapplication.model.dto.EmployeeDto;
import com.example.employeecrudapplication.validator.EmployeeValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringJUnitConfig
class EmployeeServiceImplTest {

    @InjectMocks // Данная аннотация указывает на то, что все аннотации будут заинжекчены сюда
    private EmployeeServiceImpl employeeService; // Это реальный объект, который мы будем тестировать
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeValidator employeeValidator;
    @Spy
    private EmployeeMapperImpl employeeMapper;

    @Test
    @DisplayName("Testing create if valid")
    void testCreate() {
        // Given
        Employee sampleEmployee = new Employee();
        sampleEmployee.setFirstName("Tester");
        sampleEmployee.setLastName("Testily");
        sampleEmployee.setEmail("tester.testily@mail.com");

        Employee expected = new Employee();
        expected.setFirstName("Tester");
        expected.setLastName("Testily");
        expected.setEmail("tester.testily@mail.com");
        expected.setId(1L);

        // Стаб - это переопредление поведения какого-либо метода
        Mockito.when(employeeRepository.save(any())).thenReturn(expected);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Tester");
        employeeDto.setLastName("Testily");
        employeeDto.setEmail("tester.testily@mail.com");

        // When (calling a tested method or something else, the "lab rabbit")
        Long actual = employeeService.create(employeeDto);

        // Then
        verify(employeeRepository, times(1)).save(sampleEmployee);
        assertEquals(expected.getId(), actual);
    }

    // CREATE UNHAPPY CASES FOR CREATE
    // Смотрим на метод, и ищем все опасные моменты
    //
    @Test
    @DisplayName("Testing create with invalid email")
    void testCreateWithInvalidEmail() {
        // Given
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Tester");
        employeeDto.setLastName("Testily");
        employeeDto.setEmail("tester.testily@mail..com"); // Invalid email address

        // Set up mock behavior
        doThrow(new EmployeeValidationException("Invalid email address: " + employeeDto.getEmail()))
                .when(employeeValidator).validToCreate(employeeDto);

        // When
        Throwable exception = assertThrows(EmployeeValidationException.class, () -> employeeService.create(employeeDto));

        // Then
        verify(employeeRepository, never()).save(any());
        assertEquals("Invalid email address: tester.testily@mail..com", exception.getMessage());
    }


//    @Test
//    @DisplayName("Testing create if email already exists")
//    void testCreateWhenEmailAlreadyExists() {
//        // Given
//        EmployeeDto employeeDto = new EmployeeDto();
//        employeeDto.setFirstName("Tester");
//        employeeDto.setLastName("Testily");
//        employeeDto.setEmail("tester.testily@mail.com");
//
//        Employee existingEmployee = new Employee();
//        existingEmployee.setFirstName("Nottester");
//        existingEmployee.setLastName("Nottestily");
//        existingEmployee.setEmail("tester.testily@mail.com");
//
//        Mockito.when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of.(existingEmployee));
//
//        // When
//        Throwable exception = assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.create(employeeDto));
//
//        // Then
//        verify(employeeRepository, never()).save(any());
//        assertEquals("Employee with theemail 'tester.testily@mail.com already exists", exception.getMessage());
//
//    }


//    @Test
//    @DisplayName("Testing create if invalid Email")
//    void testCreateInvalidEmail() {
//        // Given
//        Employee sampleEmployee = new Employee();
//        sampleEmployee.setFirstName("Tester");
//        sampleEmployee.setLastName("Testily");
//        sampleEmployee.setEmail("tester.testily@mail..com");
//
//        Employee expected = new Employee();
//        expected.setFirstName("Tester");
//        expected.setLastName("Testily");
//        expected.setEmail("tester.testily@mail.com");
//        expected.setId(1L);
//
//        // Стаб - это переопредление поведения какого-либо метода
//        Mockito.when(employeeRepository.save(any())).thenReturn(expected);
//
//        EmployeeDto employeeDto = new EmployeeDto();
//        employeeDto.setFirstName("Tester");
//        employeeDto.setLastName("Testily");
//        employeeDto.setEmail("tester.testily@mail.com");
//
//        // When (calling a tested method or something else, the "lab rabbit")
//        Long actual = employeeService.create(employeeDto);
//
//        // Then
//        verify(employeeRepository, times(1)).save(sampleEmployee);
//        assertEquals(expected.getId(), actual);
//    }


    @Test
    @DisplayName("Testing find by Id")
    void testFindById() {
        Long employeeId = 1L;

        Employee employee = new Employee();
        employee.setFirstName("Tester");
        employee.setLastName("Testily");
        employee.setEmail("tester.testily@mail.com");

        Optional<Employee> optionalEmployee = Optional.of(employee);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(optionalEmployee);

        EmployeeDto actual = employeeService.findById(employeeId);

        assertEquals("Tester", actual.getFirstName());
        assertEquals("Testily", actual.getLastName());
        assertEquals("tester.testily@mail.com", actual.getEmail());

    }

    @Test
    @DisplayName("Testing find all")
    void testFindAll() {
        Employee employeeTester = new Employee();
        employeeTester.setFirstName("Tester");
        employeeTester.setLastName("Testily");
        employeeTester.setEmail("tester.testily@mail.com");

        Employee employeeTester2 = new Employee();
        employeeTester2.setFirstName("Tester2");
        employeeTester2.setLastName("Testily2");
        employeeTester2.setEmail("tester2.testily@mail.com");

        Employee employeeTester23 = new Employee();
        employeeTester23.setFirstName("Tester23");
        employeeTester23.setLastName("Testily23");
        employeeTester23.setEmail("tester23.testily@mail.com");

        List<Employee> employeeList = List.of(employeeTester, employeeTester2, employeeTester23);
        List<EmployeeDto> employeeDtoList = employeeList.stream()
                .map(employeeMapper::toDto).toList();

//        Mockito.when(employeeRepository.findAll())
//                .thenReturn(List.of(employeeTester, employeeTester2, employeeTester23));

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> actual = employeeService.findAll();

        assertEquals(employeeDtoList.size(), actual.size());

        for (int i = 0; i < employeeDtoList.size(); i++) {
            assertEquals(employeeDtoList.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(employeeDtoList.get(i).getLastName(), actual.get(i).getLastName());
            assertEquals(employeeDtoList.get(i).getEmail(), actual.get(i).getEmail());
        }

//        int i = 0; // The so-called "enhanced loop", aka for-each loop
//        for (EmployeeDto expectedDto : employeeDtoList) {
//            EmployeeDto actualDto = actual.get(i++);
//            assertEquals(expectedDto.getFirstName(), actualDto.getFirstName());
//            assertEquals(expectedDto.getLastName(), actualDto.getLastName());
//            assertEquals(expectedDto.getEmail(), actualDto.getEmail());
//        }

        // Let's try to use a method reference in a lambda expression to simplify the loop
        /// To be honest, though, I don't see any "method references" here
        /// Maybe I just don't understand what a method reference is, I thought it was ::
//        IntStream.range(0, employeeDtoList.size()).forEach(i -> {
//            assertEquals(employeeDtoList.get(i).getFirstName(), actual.get(i).getFirstName());
//            assertEquals(employeeDtoList.get(i).getLastName(), actual.get(i).getLastName());
//            assertEquals(employeeDtoList.get(i).getEmail(), actual.get(i).getEmail());
//        });
    }

    @Test
    @DisplayName("Testing update with invalid email")
    void testUpdateWithInvalidEmail() {
        // Given
        Long employeeId = 1L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Tester");
        employeeDto.setLastName("Testily");
        employeeDto.setEmail("tester.testily@mail..com");

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("Old First Name");
        employee.setLastName("Old Last Name");
        employee.setEmail("oldemail@mail.com");

        // Set up mock behavior
        doThrow(new EmployeeValidationException("Invalid email address: " + employeeDto.getEmail()))
                .when(employeeValidator).validToUpdate(employeeDto);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // When
        Throwable exception = assertThrows(EmployeeValidationException.class,
                () -> employeeService.update(employeeId, employeeDto));

        // Then
        verify(employeeRepository, never()).save(any()); // This checks that the `save` method
        // of the `employeeRepository` mock object is never called during the execution
        // of the `employeeService.create(employeeDto)` method when an invalid email is provided.

        assertEquals("Invalid email address: tester.testily@mail..com", exception.getMessage());
        assertEquals("Old First Name", employee.getFirstName());
        assertEquals("Old Last Name", employee.getLastName());
        assertEquals("oldemail@mail.com", employee.getEmail());

    }

    @Test
    @DisplayName("Testing delete by Id")
    void testDeleteById() {
        // Given
        Long employeeId = 1L;

        // Stub the repository method
        doNothing().when(employeeRepository).deleteById(employeeId);

        // When
        // Call the service method
        employeeService.delete(employeeId);

        // Then
        // Verify that the repository method was called once
        verify(employeeRepository, times(1)).deleteById(employeeId);

    }

}