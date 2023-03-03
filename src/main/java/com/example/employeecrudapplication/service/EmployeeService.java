package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.model.dto.EmployeeDetailsDto;
import com.example.employeecrudapplication.model.dto.ShortEmployeeDto;

import java.util.List;

public interface EmployeeService {
    Long create(ShortEmployeeDto employeeDto);

    List<EmployeeDetailsDto> findAll();

    EmployeeDetailsDto findById(Long employeeId);

    ShortEmployeeDto update(Long employeeId, ShortEmployeeDto employeeDto);

    void delete(Long employeeId);
}
