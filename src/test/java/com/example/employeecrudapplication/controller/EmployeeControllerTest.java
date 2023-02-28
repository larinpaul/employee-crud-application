package com.example.employeecrudapplication.controller;

import com.example.employeecrudapplication.AbstractDbTest;
import com.example.employeecrudapplication.model.dto.EmployeeDto;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest extends AbstractDbTest {

    @LocalServerPort
    private int port;

    private final String BASE_PATH = "/api/v1/employees";  // Path to endpoint is always string

    @Test
    public void testGetAllEmployees() {
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

    @Test
    public void testEmployeesResponseBody() {
        given()
                .when()
                .get(BASE_PATH)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItems(1L, 2L),
                        // TODO
                        "firstName", hasItems("User-1", "User-2"),
                        "lastName", hasItems("Admin", "Supervisor"),
                        "id[0]", equalTo(1L),
                        "firstName[0]", is(equalTo("User-1")),
                        "size()", equalTo(2L)
                );
    }

    @Test
    public void testGetEmployeeWithParam() {
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
    }

    @Test
    public void extractGetEmployeeResponse1() {
        Response res = given()
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
        System.out.println("response = " + res.asString());
    }

    @Test
    public void extractGetEmployeesResponse2() {
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
    }



//    @Test
//    public void testGetAllEmployees() {
//        List<EmployeeDto> list = given()
//                .port(port)
//                .when()
//                .get(BASE_PATH) // Путь к эндпоинту
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK.value())
//                .and()
//                .extract()
//                .body()
//                .as(new TypeRef<>() {
//                }); // Возвращает результат с Эндпоинта, и мы сможем проверить что вернул Эндпоинт
//    }










    @Test
    public void testPostEmployee() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("firstName", "Newuser");
        empParams.put("lastName", "Newfamily");

        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())
                .when()
                .post(BASE_PATH)
//                .post("http://localhost:8080/employee")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("Newuser"))
                .body("lastName", equalTo("Newfamily"));
    }

}





