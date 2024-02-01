package com.sixheroes.onedayherocore.global.configuration;

import com.sixheroes.onedayherocore.oauth.OauthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(OauthProperties.class)
@Configuration
public class OauthConfiguration {
}
