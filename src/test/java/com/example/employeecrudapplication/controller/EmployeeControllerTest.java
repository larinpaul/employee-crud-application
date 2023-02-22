package com.example.employeecrudapplication.controller;

import com.example.employeecrudapplication.model.dto.EmployeeDto;
import groovy.util.logging.Slf4j;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.bytebuddy.asm.Advice;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;
    // Нужно сделать чтобы он был рандомный
    private final String BASE_PATH = "/api/v1/employees";  // Path to endpoint is always string

    @Test
    public void testGetAllEmployees() {
        List<EmployeeDto> list = given()
                .port(port)
                // . baseUri("http://localhost:8080") // После given прокидываем port
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
                .baseUri("http://localhost:8080")
                .when()
                .get(BASE_PATH)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItems(1, 2),
                        // TODO
                        "firstName", hasItems("User-1", "User-2"),
                        "lastName", hasItems("Admin", "Supervisor"),
                        "id[0]", equalTo(1),
                        "firstName[0]", is(equalTo("User-1")),
                        "size()", equalTo(2)
                );
    }

    @Test
    public void testGetEmployeeWithParam() {
        Response empResponse = given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .pathParam("id", "1")
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
    public void extractGetEmployeeResponse() {
        Response res = given()
                .baseUri("http;//localhost:8080")
                .when()
                .get("/employees")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        // TODO Remove log from lombok Checking Why Lombok is not Connected
//        log.info("response = " + res.asString()); // Как импортировать лог Ломбок?
        System.out.println("response = " + res.asString());
    }

    @Test
    public void testPostEmployee() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("firstName", "Newuser");
        empParams.put("lastName", "Newfamily");

        given()
                .contentType(ContentType.JSON)
                .body(empParams.toString())
                .when()
                .post("http://localhost:8080/employee")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("Newuser"))
                .body("lastName", equalTo("Newfamily"));
    }

}





