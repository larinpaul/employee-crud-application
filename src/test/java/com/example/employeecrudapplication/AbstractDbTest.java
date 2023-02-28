package com.example.employeecrudapplication;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
public class AbstractDbTest {

    // Попробуем поднять ТестКонтейнер, чтобы он поднимался перед тестами,
    // и чтобы все тесты баз данных наследовали AbstractDbTest.java

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:13.1-alpine")
                    .withDatabaseName("employees")
                    .withUsername("postgres")
                    .withPassword("postgres");

}
