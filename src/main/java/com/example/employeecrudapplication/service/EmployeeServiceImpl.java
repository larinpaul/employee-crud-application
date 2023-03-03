package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.data.repository.EmployeeRepository;
import com.example.employeecrudapplication.mapper.EmployeeMapper;
import com.example.employeecrudapplication.model.domain.Employee;
import com.example.employeecrudapplication.model.dto.EmployeeDetailsDto;
import com.example.employeecrudapplication.model.dto.ShortEmployeeDto;
import com.example.employeecrudapplication.validator.EmployeeValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;
    private final EmployeeMapper employeeMapper;

    public Long create(ShortEmployeeDto employeeDto) {
        employeeValidator.validToCreate(employeeDto);

        Employee employee = employeeMapper.toEntity(employeeDto);

        return employeeRepository.save(employee).getId();
    }

    public List<EmployeeDetailsDto> findAll() {
        List<Employee> all = employeeRepository.findAll();

        List<EmployeeDetailsDto> employeesDto = new ArrayList<>();

        for (Employee employee : all) {
            EmployeeDetailsDto employeeDto = employeeMapper.toDetailsDto(employee);
            employeesDto.add(employeeDto);
        }
        return employeesDto;
    }

    public EmployeeDetailsDto findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::toDetailsDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Employee not found by id: %d", employeeId))
                );
    }

    public ShortEmployeeDto update(Long employeeId, ShortEmployeeDto employeeDto) {
        employeeValidator.validToUpdate(employeeDto);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Employee not found by id: %d", employeeId))
                );

        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());

        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.toDto(updatedEmployee);
    }

    public void delete(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException(
                    String.format("Employee not found by id: %d", employeeId)
                );
        }
        employeeRepository.deleteById(employeeId);
    }

}
