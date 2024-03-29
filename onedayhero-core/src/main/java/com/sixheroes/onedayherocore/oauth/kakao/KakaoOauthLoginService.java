package com.sixheroes.onedayherocore.oauth.kakao;


import com.sixheroes.onedayherocore.oauth.OauthLoginService;
import com.sixheroes.onedayherocore.user.application.UserLoginService;
import com.sixheroes.onedayherocore.user.application.response.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOauthLoginService implements OauthLoginService {

    private final static String AUTHORIZATION_SERVER = "KAKAO";
    private final KakaoOauthClientService kakaoOauthClient;
    private final UserLoginService loginService;

    @Override
    public Boolean supports(String authorizationServer) {
        return AUTHORIZATION_SERVER.equals(authorizationServer);
    }

    @Override
    public UserAuthResponse login(String code) {
        var accessToken = kakaoOauthClient.requestToken(code);
        var userEmail = kakaoOauthClient.requestResource(accessToken);

        return loginService.login(
                kakaoOauthClient.getSocialType(),
                userEmail
        );
    }
}
