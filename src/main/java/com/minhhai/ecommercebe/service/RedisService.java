package com.minhhai.ecommercebe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value, long expirationSeconds) {
        redisTemplate.opsForValue().set(key, value, expirationSeconds, TimeUnit.SECONDS);
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) return Optional.empty();
        if (!clazz.isInstance(value)) {
            throw new IllegalArgumentException("Expected " + clazz + ", but got " + value.getClass());
        }
        return Optional.of(clazz.cast(value));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
