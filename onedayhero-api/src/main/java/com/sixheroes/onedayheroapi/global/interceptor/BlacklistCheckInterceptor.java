package com.sixheroes.onedayheroapi.global.interceptor;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.auth.InvalidAuthorizationException;
import com.sixheroes.onedayherocore.auth.application.BlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class BlacklistCheckInterceptor implements HandlerInterceptor {

    private final BlacklistService blacklistService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        var accessToken = request.getAttribute("accessToken").toString();

        if (blacklistService.check(accessToken)) {
            log.warn("탈취된 액세스 토큰으로 접근을 시도했습니다. {}", accessToken);
            throw new InvalidAuthorizationException(ErrorCode.UNAUTHORIZED_TOKEN_REQUEST);
        }

        return true;
    }
}
