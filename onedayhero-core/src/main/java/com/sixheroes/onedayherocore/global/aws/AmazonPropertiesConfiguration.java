package com.sixheroes.onedayherocore.global.aws;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AmazonProperties.class)
public class AmazonPropertiesConfiguration {
}
