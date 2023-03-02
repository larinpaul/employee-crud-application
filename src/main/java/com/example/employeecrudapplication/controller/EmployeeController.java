package com.example.employeecrudapplication.controller;

import com.example.employeecrudapplication.model.dto.EmployeeDto;
import com.example.employeecrudapplication.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    // final и аннотация RequiredArgsConstructor позволяют заинжектить без @Autowired
    private final EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.findAll();
    }

    // Get an employee by id
    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        return employeeService.findById(employeeId);
    }

    // Save an employee
    @PostMapping
    public Long createEmployee(@RequestBody EmployeeDto employee) {
        return this.employeeService.create(employee); // Автоматически смаппится объект из json в POJO
    }

    // Update an employee
    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(
            @PathVariable(value = "id") Long employeeId,
            @RequestBody EmployeeDto employeeDetails
    ) {
        return employeeService.update(employeeId, employeeDetails);
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        this.employeeService.delete(employeeId);
    }
}
