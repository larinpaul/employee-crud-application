package com.example.employeecrudapplication;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.junit.jupiter.api.Assumptions.assumeTrue;


@Testcontainers
@ContextConfiguration(initializers = {AbstractDbTest.Initializer.class})
public class AbstractDbTest {

    // Попробуем поднять ТестКонтейнер, чтобы он поднимался перед тестами,
    // и чтобы все тесты баз данных наследовали AbstractDbTest.java

    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:13.1-alpine")
                    .withDatabaseName("employees")
                    .withUsername("postgres")
                    .withPassword("postgres");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresContainer.getUsername(),
                    "spring.datasource.password=" + postgresContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    static {
        postgresContainer.start();
    }

    @BeforeAll
    public static void checkPostgresContainerIsRunning() {
        assumeTrue(postgresContainer.isRunning());
    }

}
