package com.sixheroes.onedayheroapi.docs;

import com.sixheroes.onedayherocore.global.jwt.JwtProperties;
import com.sixheroes.onedayherocore.global.jwt.JwtTokenManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class JwtTestConfiguration {

    private final static String TEST_SECRET_KEY = "testtesttesttesttesttesttesttesttesttesttesttesttesttest";
    private final static Long TEST_EXPIRY_TIME_MS = 60000000000L;

    @Bean("testJwtProperties")
    public JwtProperties jwtProperties() {
        return new JwtProperties(
                TEST_SECRET_KEY,
                TEST_EXPIRY_TIME_MS,
                TEST_EXPIRY_TIME_MS,
                "id",
                "role"
        );
    }

    @Bean("testJwtTokenManager")
    public JwtTokenManager JwtTokenManager(JwtProperties testJwtProperties) {
        return new JwtTokenManager(testJwtProperties);
    }
}
