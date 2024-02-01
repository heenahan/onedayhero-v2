package com.sixheroes.onedayherocore.oauth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth")
public class OauthProperties {

    private final Kakao kakao;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Kakao {
        private String authorizationServer;
        private String clientId;
        private String redirectUri;
        private String loginPageRedirectUri;
    }
}
