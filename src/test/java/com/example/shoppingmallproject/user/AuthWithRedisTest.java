package com.example.shoppingmallproject.user;

import com.example.shoppingmallproject.common.redis.RedisConfig;
import com.example.shoppingmallproject.common.redis.RedisDAO;
import com.example.shoppingmallproject.user.dto.TokenResponseDto;
import com.example.shoppingmallproject.user.service.UserService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class AuthWithRedisTest {

    private static final int REDIS_PORT = 6379;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisDAO redisDAO;

    public static GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(REDIS_PORT);

    @BeforeAll
    public static void setup() {
        redisContainer.start();


        System.setProperty("spring.data.redis.host", redisContainer.getHost());
        System.setProperty("spring.data.redis.port", String.valueOf(redisContainer.getMappedPort(REDIS_PORT)));
        System.out.println(redisContainer.getHost());
        System.out.println(String.valueOf(redisContainer.getMappedPort(REDIS_PORT)));
    }

    @AfterAll
    public static void cleanup() {
        redisContainer.stop();
    }

    @Test
    void testSaveAndGetFromRedis() {
        // Given
        String key = "access";
        String value = "refresh";

        TokenResponseDto dto = TokenResponseDto.of(key, value);

        // When
        redisDAO.simpleSetValue(key, value, Duration.ofMillis(100));

        // Then
        String retrievedValue = redisDAO.simpleGetValue(key);
        assertEquals(value, retrievedValue);
    }
}
