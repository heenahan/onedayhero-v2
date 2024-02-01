package com.sixheroes.onedayherocore.auth.application;


import com.sixheroes.onedayherocore.auth.infra.repository.BlacklistRepository;
import com.sixheroes.onedayherocore.auth.infra.repository.RefreshTokenRepository;
import com.sixheroes.onedayherocore.auth.application.response.ReissueTokenResponse;
import com.sixheroes.onedayherocore.global.jwt.JwtProperties;
import com.sixheroes.onedayherocore.global.jwt.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    public static final String DEFAULT_ROLE = "MEMBER";

    private final JwtProperties jwtProperties;
    private final JwtTokenManager jwtTokenManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistRepository blacklistRepository;

    public String generateRefreshToken(
            Long userId
    ) {
        var refreshToken = RefreshTokenGenerator.generate();
        var refreshTokenValue = RefreshTokenSerializer.serialize(RefreshToken.issue(userId));
        var duration = Duration.ofMillis(jwtProperties.getRefreshTokenExpiryTimeMs());

        refreshTokenRepository.save(
                refreshToken,
                refreshTokenValue,
                duration
        );

        return refreshToken;
    }

    public void deleteRefreshToken(
            String refreshToken
    ) {
        refreshTokenRepository.delete(refreshToken);
    }

    public Optional<RefreshToken> findRefreshTokenValue(
            String refreshToken
    ) {
        return refreshTokenRepository.find(refreshToken)
                .map(RefreshTokenSerializer::deserialize);
    }

    public ReissueTokenResponse rotation(
            String refreshToken,
            RefreshToken refreshTokenValue
    ) {
        if (!refreshTokenValue.validateChainable()) {
            var chainedToken = refreshTokenValue.getChainedToken();
            refreshTokenRepository.delete(refreshToken);
            refreshTokenRepository.delete(chainedToken);

            return ReissueTokenResponse.fail();
        }

        var newRefreshToken = generateRefreshToken(refreshTokenValue.getUserId());
        var newAccessToken = jwtTokenManager.generateAccessToken(
                refreshTokenValue.getUserId(),
                DEFAULT_ROLE
        );

        refreshTokenValue.setTokenChain(newRefreshToken);
        refreshTokenRepository.update(
                refreshToken,
                RefreshTokenSerializer.serialize(refreshTokenValue),
                Duration.ofMillis(refreshTokenRepository.getExpireMsTime(refreshToken))
        );

        return ReissueTokenResponse.success(
                newAccessToken,
                newRefreshToken
        );
    }
}
