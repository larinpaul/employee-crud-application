package com.example.employeecrudapplication.data.repository;

import com.example.employeecrudapplication.model.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
