package com.example.employeecrudapplication.controller;

import com.example.employeecrudapplication.AbstractDbTest;
import com.example.employeecrudapplication.data.repository.EmployeeRepository;
import com.example.employeecrudapplication.model.domain.Employee;
import com.example.employeecrudapplication.model.dto.EmployeeDto;
import com.example.employeecrudapplication.service.EmployeeService;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest extends AbstractDbTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final String BASE_PATH = "/api/v1/employees";  // Path to endpoint is always string

    @Test
    void testGetAllEmployees() {
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
                }); // Возвращает результат с Эндпоинта, и мы сможем проверить что вернул Эндпоинт
    }

//    @Test
//    void testEmployeesResponseBody() {
//        given()
//                .when()
//                .get(BASE_PATH)
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK.value())
//                .body("id", hasItems(1L, 2L),
//                        // TODO
//                        "firstName", hasItems("User-1", "User-2"),
//                        "lastName", hasItems("Admin", "Supervisor"),
//                        "id[0]", equalTo(1L),
//                        "firstName[0]", is(equalTo("User-1")),
//                        "size()", equalTo(2L)
//                );
//    }

    @Test
    void testEmployeesResponseBody() throws JSONException {
        JSONObject employeeParams = new JSONObject();
        employeeParams.put("firstName", "Newuser1");
        employeeParams.put("lastName", "Newfamily1");
        employeeParams.put("firstName", "Newuser2");
        employeeParams.put("lastName", "Newfamily2");

        given()
                .port(port)
                .when()
                .get(BASE_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItems(1L, 2L),
                    "name", hasItems("Newuser1", "Newuser2"),
                    "lastName", hasItems("Newfamily2", "Newfamily2"),
                    "id[0]", equalTo(1L),
                    "id[1]", equalTo(2L),
                    "lastName[0]", is(equalTo("Newuser1")),
                    "lastName[1]", is(equalTo("Newuser2")),
                    "size()", equalTo(2)
                );
    }


    /*@Test
    void testGetEmployeeWithParam() {
        Response empResponse = given()
                .contentType(ContentType.JSON)
//                .pathParam("id", "1") // Удалю эту строку, потому что она дублирует функционал .get()
                .when()
                .get(BASE_PATH + "/{id}", "1")
                // Когда хочешь проверить получение сущности по айди, и
                // если она не найдена, получается 404
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        // TODO
        // Внутри РестАщюрд и Спринга есть маппер, там не нужно передавать стрингу полями
        // Можно проинициализировать обычный объект, который ты хочешь передать без маппинга
        // ТАК ЖЕ, как и в других тестах, ты создавал объект, метод, и ты передавать будешь не метод, а боди
        JsonPath jsonPathObj = empResponse.jsonPath();
        Assertions.assertEquals(jsonPathObj.getLong("id"), 1L);
        Assertions.assertEquals(jsonPathObj.getString("firstName"), "User-1");
        Assertions.assertEquals(jsonPathObj.getString("lastName"), "Admin");
    }*/

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


   /* @Test
    void getEmployeeById() {
        Employee employee = new Employee();
        Employee employee = new Employee();
        Employee employee = new Employee();
        Employee employee = new Employee();
        Employee employee = new Employee();

        employeeDto.setFirstName("Newuser");
        employeeDto.setLastName("Newfamily");
        employeeDto.setEmail("Newuser@mail.com");

        employeeDto.setFirstName("Newuser");
        employeeDto.setLastName("Newfamily");
        employeeDto.setEmail("Newuser@mail.com");

        // Это лишь объявление сущностей,
        // а их нужно создать в БД

        // Метод employeeRepository.save()

        // они появляются в БД

        // А потом вытаскивать через Эндпоинт

    }
*/

    /*@Test
    void extractGetEmployeeResponse1() { // логика тут не совпадает с названием...
        List<EmployeeDto> list = given()
                .port(port)
                .when()
                .get(BASE_PATH)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
//                .response()
                .body()
                .as(new TypeRef<>() {
                });
//        System.out.println("response = " + res.asString());
    }*/

    /*@Test
    void extractGetEmployeesResponse2() {
        Response res = given().
                // baseUri("http://localhost:8080").
                        when().
                get("/employees").
                then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("response = " + res.asString());
    }*/

    @Test
    public void testPostEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setFirstName("Newuser");
        employeeDto.setLastName("Newfamily");
        employeeDto.setEmail("Newuser@mail.com");
        // JSON Object не нужно использовать, RestAssured сам может маппить

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
                    // expected                 // actual

    }

    @Test
    void testUpdateEmployee() {
        // Create an employee
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Newuser");
        employeeDto.setLastName("Newfamily");;
        employeeDto.setEmail("Newuser@mail.com");
        Employee employee = new Employee();
//        Employee savedEmployee = employeeRepository.save(employee);
//        Long employeeId = savedEmployee.getId();
        Long employeeId = employeeRepository.save(employee).getId();

        // Update the employee
        EmployeeDto updatedEmployeeDto = new EmployeeDto();
        updatedEmployeeDto.setFirstName("Updateduser");
        updatedEmployeeDto.setLastName("Updatedfamily");
        updatedEmployeeDto.setEmail("Updateduser@mail.com");

        // Verify the status code
        Long actual =
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
                .as((Type) EmployeeDto.class);

        assertNotNull(actual);

        Optional<Employee> byId = employeeRepository.findById(actual);

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





