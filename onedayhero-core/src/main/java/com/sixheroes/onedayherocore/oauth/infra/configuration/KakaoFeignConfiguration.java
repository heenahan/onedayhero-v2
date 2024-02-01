package com.sixheroes.onedayherocore.oauth.infra.configuration;

import com.sixheroes.onedayherocore.oauth.infra.feign.decoder.LoginErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoFeignConfiguration {

    @Bean
    public LoginErrorDecoder loginErrorDecoder() {
        return new LoginErrorDecoder();
    }
}
