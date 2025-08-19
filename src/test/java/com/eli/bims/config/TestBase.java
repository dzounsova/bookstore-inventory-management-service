package com.eli.bims.config;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBase {

    static protected final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.2"));
        postgres.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration,classpath:db/dev");
    }
}
