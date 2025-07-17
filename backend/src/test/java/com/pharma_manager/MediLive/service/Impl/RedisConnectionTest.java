package com.pharma_manager.MediLive.service.Impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisSetAndGet() {
        // Set a value in Redis
        redisTemplate.opsForValue().set("test", "sup bitch");

        // Retrieve the value
        String value = redisTemplate.opsForValue().get("test");

        // Assert that the value is correctly retrieved
        assertThat(value).isEqualTo("sup bitch");
    }
}

