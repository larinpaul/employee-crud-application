package com.example.employeecrudapplication.controller;

import com.example.employeecrudapplication.model.EmployeeDto;
import com.example.employeecrudapplication.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.employeecrudapplication.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.employeecrudapplication.data.repository.EmployeeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    // final и аннотация RequiredArgsConstructor позволяют заинжектить без @Autowired
    private final EmployeeService employeeService;

    // Get employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    // Get an employee by id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeService.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    // Save an employee
    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeDto employee) {
        return this.employeeService.createEmployee(employee); // Ручками СМАППИТЬ из одной сущности в другую В СЕРВИСЕ
    }

    // Update an employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   /* RE-DO! @Valid Validate in a separate component!*/
                                                   @RequestBody Employee employeeDetails) {

        // Эту всю логику переносим в Сервис и делаем так, чтобы было не через Employee, а через EmployeeDTO
        Employee employee = employeeService.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found for this id :: " + employeeId));

        // Это по сути маппинг полей, нужно что-то подобное сделать для всех методов через DTO
        // И разобраться как получить список из БД не список Employee, а список EmployeeDTO
        // (Можем посмотреть как в проекте с Лямбдами findById() order() findAll() итд)
        employee.setEmail(employeeDetails.getEmail());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        // С МАППИНГОМ нужно очень разобраться, потому что будешь видеть ЧАСТО
        // Разобраться как маппится из одного типа в другой
        // Посмотри в сторону MapStruct, попробуй применить
        // Если применишь,
        // Если подружишь Ломбок с МапСтрактом, то вообще сразу на проект :D

        return ResponseEntity.ok(employeeService.save(employee));
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeService.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found for this id :: " + employeeId));

        this.employeeService.delete(employee);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
