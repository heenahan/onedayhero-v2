package com.sixheroes.onedayherocore.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter
@Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey;
    private Long accessTokenExpiryTimeMs;
    private Long refreshTokenExpiryTimeMs;
    private String claimId;
    private String claimRole;
}
