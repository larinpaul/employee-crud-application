package com.example.employeecrudapplication.mapper;

import com.example.employeecrudapplication.model.domain.Employee;
import com.example.employeecrudapplication.model.dto.EmployeeDetailsDto;
import com.example.employeecrudapplication.model.dto.ShortEmployeeDto;
import org.mapstruct.Mapper;

// Now you can create methods that can map employee to employeeDto
// according to the names of the fields
// you can see if it will work
// Ты просто указываешь тут что на вход и что на выход, и он понимает,
// что по сути из employee копирует в employeeDto
// Если названия не совпадают, то используется аннотация @Mapping
@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    ShortEmployeeDto toDto(Employee employee);

    EmployeeDetailsDto toDetailsDto(Employee employee);

    Employee toEntity(ShortEmployeeDto employeeDto); //


}
