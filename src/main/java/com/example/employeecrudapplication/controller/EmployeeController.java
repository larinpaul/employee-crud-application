package com.example.employeecrudapplication.controller;

import com.example.employeecrudapplication.model.dto.EmployeeDetailsDto;
import com.example.employeecrudapplication.model.dto.ShortEmployeeDto;
import com.example.employeecrudapplication.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDetailsDto> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeDetailsDto getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        return employeeService.findById(employeeId);
    }

    @PostMapping
    public Long createEmployee(@RequestBody ShortEmployeeDto employee) {
        return this.employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public ShortEmployeeDto updateEmployee(
            @PathVariable(value = "id") Long employeeId,
            @RequestBody ShortEmployeeDto employeeDetails
    ) {
        return employeeService.update(employeeId, employeeDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        this.employeeService.delete(employeeId);
    }
}
