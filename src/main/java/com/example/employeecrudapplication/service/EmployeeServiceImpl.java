package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.data.repository.EmployeeRepository;
import com.example.employeecrudapplication.mapper.EmployeeMapper;
import com.example.employeecrudapplication.model.domain.Employee;
import com.example.employeecrudapplication.model.dto.EmployeeDto;
import com.example.employeecrudapplication.validator.EmployeeValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;
    private final EmployeeMapper employeeMapper;

    public Long create(EmployeeDto employeeDto) {
        employeeValidator.validToCreate(employeeDto);

        Employee employee = employeeMapper.toEntity(employeeDto);

        return employeeRepository.save(employee).getId();
    }

    public List<EmployeeDto> findAll() {
        List<Employee> all = employeeRepository.findAll();

        List<EmployeeDto> employeesDto = new ArrayList<>();

        for (Employee employee : all) {
            EmployeeDto employeeDto = employeeMapper.toDto(employee);
            employeesDto.add(employeeDto);
        } // Вообще это можно сделать в одну конструкцию, использовав streamAPI (можно поискать потом)
        return employeesDto;
        // Для тестирования мы сперва МОКАЕМ репозиторий
        // Мок ничего не может возвращаеть,
        // Поэтому findAll мы можем застабить
        //
        // И здесь ещё намечается один спай.
        // Вообще это маппер, но это долго, поэтому можно иногда
        // использовать спай.
    }

    public EmployeeDto findById(Long employeeId) { // Не воспринимай метод как магию, РАЗБЕРИСЬ что в нем происходит
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public EmployeeDto update(Long employeeId, EmployeeDto employeeDto) { // DTO - Мы берём от пользователя
        employeeValidator.validToUpdate(employeeDto);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found for this id :: " + employeeId));

        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());

        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.toDto(updatedEmployee);
    }

    public void delete(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

}
