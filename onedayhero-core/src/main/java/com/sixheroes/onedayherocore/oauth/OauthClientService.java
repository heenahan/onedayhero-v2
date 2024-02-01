package com.sixheroes.onedayherocore.oauth;


public interface OauthClientService {

    String getSocialType();

    String requestToken(String code);

    String requestResource(String accessToken);
}
