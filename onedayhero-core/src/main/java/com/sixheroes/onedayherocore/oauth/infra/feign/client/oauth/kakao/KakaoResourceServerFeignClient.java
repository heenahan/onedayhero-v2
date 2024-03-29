package com.sixheroes.onedayherocore.oauth.infra.feign.client.oauth.kakao;

import com.sixheroes.onedayherocore.oauth.infra.configuration.KakaoFeignConfiguration;
import com.sixheroes.onedayherocore.oauth.infra.feign.client.oauth.kakao.response.KakaoResourceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
        name = "kakao-user-me",
        url = "https://kapi.kakao.com/v2/user/me",
        configuration = KakaoFeignConfiguration.class
)
public interface KakaoResourceServerFeignClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoResourceResponse userInfoRequest(@RequestHeader("Authorization") String bearerAccessToken);
}
