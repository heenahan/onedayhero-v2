package com.sixheroes.onedayherocore.global.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@ConfigurationProperties(prefix = "spring.data.redis")
@RequiredArgsConstructor
public class RedisProperties {

    private final String host;
    private final Integer port;
}
