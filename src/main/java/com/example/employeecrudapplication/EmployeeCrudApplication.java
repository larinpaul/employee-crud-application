package com.example.employeecrudapplication;

import com.example.employeecrudapplication.service.EmployeeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeCrudApplication.class, args);

        EmployeeService callToAction = new EmployeeService();
        callToAction.printMotivationalMessage();
    }

}
