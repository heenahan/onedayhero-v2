package com.sixheroes.onedayherocore.auth.infra.repository;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class BlacklistRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public BlacklistRepository(
            @Qualifier("authRedisTemplate") RedisTemplate<String, String> authRedisTemplate
    ) {
        this.redisTemplate = authRedisTemplate;
    }

    public Boolean exist(
            String key
    ) {
        return redisTemplate.opsForValue().get(key) != null;
    }

    public void save(
            String key,
            String value,
            Duration duration
    ) {
        redisTemplate.opsForValue().set(key, value, duration);
    }
}
