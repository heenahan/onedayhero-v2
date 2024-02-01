package com.sixheroes.onedayheroapi.docs;

import com.sixheroes.onedayherocore.oauth.OauthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthTestConfiguration {

    @Bean
    public OauthProperties oauthProperties() {
        var kakao = new OauthProperties.Kakao("KAKAO", "clientId", "redirectUri", "redirectURi");

        return new OauthProperties(kakao);
    }
}
