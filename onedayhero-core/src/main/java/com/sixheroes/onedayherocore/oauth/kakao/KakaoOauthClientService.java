package com.sixheroes.onedayherocore.oauth.kakao;

import com.sixheroes.onedayherocore.oauth.OauthClientService;
import com.sixheroes.onedayherocore.oauth.OauthProperties;
import com.sixheroes.onedayherocore.oauth.infra.feign.client.oauth.kakao.KakaoAuthServerFeignClient;
import com.sixheroes.onedayherocore.oauth.infra.feign.client.oauth.kakao.KakaoResourceServerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class KakaoOauthClientService implements OauthClientService {

    private final OauthProperties oauthKakaoProperties;
    private final KakaoAuthServerFeignClient kakaoAuthServerFeignClient;
    private final KakaoResourceServerFeignClient kakaoResourceServerFeignClient;

    @Override
    public String getSocialType() {
        return oauthKakaoProperties.getKakao()
                .getAuthorizationServer();
    }

    @Override
    public String requestToken(String code) {
        return kakaoAuthServerFeignClient
                .requestToken(getParamMap(code))
                .accessToken();
    }

    @Override
    public String requestResource(String accessToken) {
        return kakaoResourceServerFeignClient
                .userInfoRequest(plusBearerType(accessToken))
                .getEmail();
    }

    private String plusBearerType(String accessToken) {
        return "Bearer " + accessToken;
    }

    private Map<String, String> getParamMap(String code) {
        return Map.of(
                "client_id", oauthKakaoProperties.getKakao().getClientId(),
                "redirect_uri", oauthKakaoProperties.getKakao().getRedirectUri(),
                "code", code,
                "grant_type", "authorization_code"
        );
    }
}
