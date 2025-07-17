package com.pharma_manager.MediLive.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setValue(String key, Object o, Long expiry) {
//        System.out.println("Redis is connecting to: " + redisTemplate.getConnectionFactory().getConnection().getClientName());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(o), expiry, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception in RedisServiceImpl", e);
        }
    }

    // Retrieve a value from Redis by key
    public <T> List<T> getValue(String key, Class<T> entity) {
//        System.out.println("Redis is connecting to: " + redisTemplate.getConnectionFactory().getConnection().getClientName());

        try {
            String o = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(o, objectMapper.getTypeFactory().constructCollectionType(List.class, entity));
        } catch (Exception e) {
            log.error("Exception in RedisServiceImpl", e);
            return null;
        }
    }
}
