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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringJUnitConfig
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;
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

        Mockito.when(employeeRepository.save(any())).thenReturn(expected);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Tester");
        employeeDto.setLastName("Testily");
        employeeDto.setEmail("tester.testily@mail.com");

        // When
        Long actual = employeeService.create(employeeDto);

        // Then
        verify(employeeRepository, times(1)).save(sampleEmployee);
        assertEquals(expected.getId(), actual);
    }

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

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> actual = employeeService.findAll();

        assertEquals(employeeDtoList.size(), actual.size());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(employeeDtoList);
    }

    @Test
    @DisplayName("Testing not throwing an exception with a valid email")
    void testUpdateWithValidEmailNotThrowing() {
        // Given
        long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("Oldname");
        employee.setLastName("Oldlastname");
        employee.setEmail("oldemail@mail.com");

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Newname");
        employeeDto.setLastName("Newlastname");
        employeeDto.setEmail("newemail@mail.com");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(employeeId);
        updatedEmployee.setFirstName(employeeDto.getFirstName());
        updatedEmployee.setLastName(employeeDto.getLastName());
        updatedEmployee.setEmail(employeeDto.getEmail());

        // Set up mock behavior
        doNothing().when(employeeValidator).validToUpdate(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // When
        assertDoesNotThrow(() -> employeeService.update(employeeId, employeeDto));

        // Then
        verify(employeeValidator).validToUpdate(employeeDto);
    }

    @Test
    @DisplayName("Testing update with a valid email")
    void testUpdateWithValidEmail() {
        // Given
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("Oldname");
        employee.setLastName("Oldlastname");
        employee.setEmail("oldemail@mail.com");

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Newname");
        employeeDto.setLastName("Newlastname");
        employeeDto.setEmail("newemail@mail.com");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(employeeId);
        updatedEmployee.setFirstName(employeeDto.getFirstName());
        updatedEmployee.setLastName(employeeDto.getLastName());
        updatedEmployee.setEmail(employeeDto.getEmail());

        // Set up mock behavior
        doNothing().when(employeeValidator).validToUpdate(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // When
        EmployeeDto result = employeeService.update(employeeId, employeeDto);

        // Then
        verify(employeeRepository).save(any(Employee.class));
        assertNotNull(result);
        assertEquals(employeeId, updatedEmployee.getId());
        assertEquals(employeeDto.getFirstName(), result.getFirstName());
        assertEquals(employeeDto.getLastName(), result.getLastName());
        assertEquals(employeeDto.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("Testing update with an invalid email")
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
        verify(employeeRepository, never()).save(any());

        assertEquals("Invalid email address: tester.testily@mail..com", exception.getMessage());
    }

    @Test
    @DisplayName("Testing delete by Id")
    void testDeleteById() {
        // Given
        Long employeeId = 1L;

        // Stub the repository method
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(employeeId);

        // When
        // Call the service method
        employeeService.delete(employeeId);

        // Then
        // Verify that the repository method was called once
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}