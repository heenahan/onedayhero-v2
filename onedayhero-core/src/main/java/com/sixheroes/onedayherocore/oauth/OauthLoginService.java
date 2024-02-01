package com.sixheroes.onedayherocore.oauth;

import com.sixheroes.onedayherocore.user.application.response.UserAuthResponse;

public interface OauthLoginService {

    Boolean supports(String authorizationServer);

    UserAuthResponse login(String code);
}
