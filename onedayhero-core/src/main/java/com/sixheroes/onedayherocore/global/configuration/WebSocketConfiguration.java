package com.sixheroes.onedayherocore.global.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(
            MessageBrokerRegistry registry
    ) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * @addEndpoint : 소켓 연결 uri 를 지정한다.
     * @setAllowedOriginPatterns : cors 설정, 향후 localhost:80, localhost:443 으로 변경
     * @withSockJs() : webSocket 을 사용 할 수 없는 브라우저에서 webSocket이 적용되는 것처럼 보이게 하는 라이브러리 적용
     * - webSocket을 지원하지 않는다면 다음과 같이 변경된다. streaming -> polling
     */
    @Override
    public void registerStompEndpoints(
            StompEndpointRegistry registry
    ) {
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*");
    }
}
