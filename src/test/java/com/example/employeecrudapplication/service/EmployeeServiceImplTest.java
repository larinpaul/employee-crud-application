package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.data.repository.EmployeeRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


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

        assertEquals(actual.getFirstName(), "Tester");
        assertEquals(actual.getLastName(), "Testily");
        assertEquals(actual.getEmail(), "tester.testily@mail.com");

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
                .map(employeeMapper::toDto).collect(Collectors.toList());

//        Mockito.when(employeeRepository.findAll())
//                .thenReturn(List.of(employeeTester, employeeTester2, employeeTester23));

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDto> actual = employeeService.findAll();

        assertEquals(employeeDtoList.size(), actual.size());

//        for (int i = 0; i < employeeDtoList.size(); i++) {
//            assertEquals(employeeDtoList.get(i).getFirstName(), actual.get(i).getFirstName());
//            assertEquals(employeeDtoList.get(i).getLastName(), actual.get(i).getLastName());
//            assertEquals(employeeDtoList.get(i).getEmail(), actual.get(i).getEmail());
//        }
//
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
        IntStream.range(0, employeeDtoList.size()).forEach(i -> {
            assertEquals(employeeDtoList.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(employeeDtoList.get(i).getLastName(), actual.get(i).getLastName());
            assertEquals(employeeDtoList.get(i).getEmail(), actual.get(i).getEmail());
        });


    }

    public List<EmployeeDto> findAll() {
        List<Employee> all = employeeRepository.findAll();

        List<EmployeeDto> employeesDto = new ArrayList<>();

        for (Employee employee : all) {
            EmployeeDto employeeDto = employeeMapper.toDto(employee);
            employeesDto.add(employeeDto);
        } // Вообще это можно сделать в одну конструкцию, использовав streamAPI (можно поискать потом)
        return employeesDto;

    }





}