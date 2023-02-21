package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    Long create(EmployeeDto employeeDto);
    List<EmployeeDto> findAll();
    EmployeeDto findById(Long employeeId);
    EmployeeDto update(Long employeeId, EmployeeDto employeeDto);
    void delete(Long employeeId);
}
