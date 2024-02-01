package com.sixheroes.onedayherocore.global.configuration;

import com.sixheroes.onedayherocore.global.jwt.JwtProperties;
import com.sixheroes.onedayherocore.global.jwt.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
@Configuration
public class JwtConfiguration {

    @Bean
    public JwtTokenManager jwtTokenManager(JwtProperties jwtProperties) {
        return new JwtTokenManager(jwtProperties);
    }
}

