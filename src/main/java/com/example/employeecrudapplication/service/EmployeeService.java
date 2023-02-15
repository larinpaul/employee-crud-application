package com.example.employeecrudapplication.service;

import com.example.employeecrudapplication.data.repository.EmployeeRepository;
import com.example.employeecrudapplication.model.Employee;
import com.example.employeecrudapplication.model.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    // заинжектить в сервисе репозиторий как ты делал в контроллере (а ещё лучше через Ломбок)
    // и создать методы как в контроллере, гетбайайти и тд.
    // затем инжектим сервис в контроллер и вызываем в контроллере соответствующие методы из сервиса

    // @ 3. Constructor Injection With Lombok https://www.baeldung.com/spring-injection-lombok
    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator validator;

    public Employee createEmployee(EmployeeDto employee) {
        validator.validToCreate(employee);
        // Сделать Маппер.
        // Берешь EmployeeDto, создаешь объект класса Employee
        // Прокидываешь поля из DTO в Entity, то есть из EmployeeDto берём поля, и каждое поле маппим в Employee
        // На выходе получится объект класса Employee, который сохраняешь в базу данных

        return this.employeeRepository.save(employee);
    }




}
