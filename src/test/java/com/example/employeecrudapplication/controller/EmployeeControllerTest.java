package com.example.employeecrudapplication.controller;

import com.example.employeecrudapplication.AbstractDbTest;
import com.example.employeecrudapplication.data.repository.EmployeeRepository;
import com.example.employeecrudapplication.exception.ErrorResponse;
import com.example.employeecrudapplication.model.domain.Employee;
import com.example.employeecrudapplication.model.dto.EmployeeDto;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest extends AbstractDbTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final String BASE_PATH = "/api/v1/employees";

    @Test
    void testGetAllEmployeesEmptyList() {
        List<EmployeeDto> list = given()
                .port(port)
                .when()
                .get(BASE_PATH)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(new TypeRef<>() {
                });

        // Assert that the list is returned by the API and is empty
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    void testGetAllEmployeesUnhappy() {
        List<EmployeeDto> list = given()
                .port(port)
                .when()
                .get(BASE_PATH) // Путь к эндпоинту
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(new TypeRef<>() {
                });
    }

    @Test
    void shouldObtainEmployee() {
        List<EmployeeDto> list = given()
                .port(port)
                .when()
                .get(BASE_PATH)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .response()
                .as(new TypeRef<>() {
                });
    }

    @Test
    void testGetEmployeeById() {
        // Create an employee in the DB
        Employee employee = new Employee();
        employee.setFirstName("Newuser1");
        employee.setLastName("Newfamily1");
        employee.setEmail("Newuser@mail.com");
        Long employeeId = employeeRepository.save(employee).getId();

        // Create an expected employee
        EmployeeDto expected = new EmployeeDto();
        expected.setFirstName("Newuser1");
        expected.setLastName("Newfamily1");
        expected.setEmail("Newuser@mail.com");

        // Validate status code and extract the body of the request
        EmployeeDto actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_PATH + "/" + employeeId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(EmployeeDto.class);

        // Check if the returned employee matches the expected one
        assertNotNull(actual);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    void testGetEmployeeByIdNotFound() {
        // Given
        Long employeeId = 9400400400L;

        // When
        EntityNotFoundException exception = given()
                .port(port)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(BASE_PATH + "/" + employeeId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .body()
                .as(EntityNotFoundException.class);
    }

    @Test
    void testDeleteEmployeeById() {
        // Create an employee in the DB
        Employee employee = new Employee();
        employee.setFirstName("Newuser1");
        employee.setLastName("Newfamily1");
        employee.setEmail("Newuser1@mail.com");
        Long employeeId = employeeRepository.save(employee).getId();

        // Delete the employee by Id
        given()
                .port(port)
                .when()
                .delete(BASE_PATH + "/" + employeeId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        // Verify that the employee has been deleted
        boolean byId = employeeRepository.existsById(employeeId);
        assertFalse(byId);
    }

    @Test
    public void testDeleteEmployeeByIdNotFound() {
        // Given
        Long employeeId = 9400400400L;

        // When
        EntityNotFoundException exception = given()
                .port(port)
                .when()
                .delete(BASE_PATH + "/" + employeeId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .body()
                .as(EntityNotFoundException.class);
    }

    @Test
    public void testPostEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setFirstName("Newuser");
        employeeDto.setLastName("Newfamily");
        employeeDto.setEmail("Newuser@mail.com");

        Long actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(employeeDto)
                .when()
                .post(BASE_PATH)
//                .post("http://localhost:8080/employee")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(Long.class);

        assertNotNull(actual);

        Optional<Employee> byId = employeeRepository.findById(actual);

        assertFalse(byId.isEmpty());

        Employee employee = byId.get();

        assertEquals(employeeDto.getFirstName(), employee.getFirstName());
        assertEquals(employeeDto.getLastName(), employee.getLastName());
        assertEquals(employeeDto.getEmail(), employee.getEmail());
    }

    @Test
    void testPostEmployeeIncorrectEmail() {
        // Given
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Newuser");
        employeeDto.setLastName("Newfamily");
        employeeDto.setEmail("invalidemail");

        // When
        ErrorResponse errorResponse = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(employeeDto)
                .when()
                .post(BASE_PATH)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .body()
                .as(ErrorResponse.class);

        // Then
        assertNotNull(errorResponse);
    }

    @Test
    void testUpdateEmployee() {
        // Create an employee
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Newuser");
        employeeDto.setLastName("Newfamily");
        ;
        employeeDto.setEmail("Newuser@mail.com");
        Employee employee = new Employee();
        Long employeeId = employeeRepository.save(employee).getId();

        // Update the employee
        EmployeeDto updatedEmployeeDto = new EmployeeDto();
        updatedEmployeeDto.setFirstName("Updateduser");
        updatedEmployeeDto.setLastName("Updatedfamily");
        updatedEmployeeDto.setEmail("Updateduser@mail.com");

        // Verify the status code and extract the body
        EmployeeDto actual =
                given()
                        .port(port)
                        .contentType(ContentType.JSON)
                        .body(updatedEmployeeDto)
                        .when()
                        .put(BASE_PATH + "/" + employeeId)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .and()
                        .extract()
                        .body()
                        .as(EmployeeDto.class);

        assertNotNull(actual);

        Optional<Employee> byId = employeeRepository.findById(employeeId);

        assertFalse(byId.isEmpty());

        Employee updatedEmployee = byId.get();

        // Verify the employee is updated
        assertEquals(updatedEmployeeDto.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(updatedEmployeeDto.getLastName(), updatedEmployee.getLastName());
        assertEquals(updatedEmployeeDto.getEmail(), updatedEmployee.getEmail());

    }


    @AfterEach
    void clearDbData() {
        employeeRepository.deleteAll();
    }

}





