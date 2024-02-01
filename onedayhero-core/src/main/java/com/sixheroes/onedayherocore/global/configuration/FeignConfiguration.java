package com.sixheroes.onedayherocore.global.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.sixheroes.onedayherocore.oauth.infra.feign")
@Configuration
public class FeignConfiguration {
}
