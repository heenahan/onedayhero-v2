package com.sixheroes.onedayheroapi.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sixheroes.onedayheroapi.docs.JwtTestConfiguration;
import com.sixheroes.onedayheroapi.global.argumentsresolver.authuser.AuthUserArgumentResolver;
import com.sixheroes.onedayheroapi.global.handler.GlobalExceptionHandler;
import com.sixheroes.onedayheroapi.global.interceptor.JwtAuthInterceptor;
import com.sixheroes.onedayherocore.global.jwt.JwtProperties;
import com.sixheroes.onedayherocore.global.jwt.JwtTokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension.class)
@Import(JwtTestConfiguration.class)
public abstract class RestDocsSupport {

    @Autowired
    private JwtProperties testJwtProperties;

    @Autowired
    private JwtTokenManager testJwtTokenManager;

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(setController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(), new AuthUserArgumentResolver(testJwtProperties))
                .addMappedInterceptors(new String[]{
                        "/api/v1/alarms/**",
                        "/api/v1/sse/**",
                        "/api/v1/chat-rooms/**",
                        "/api/v1/mission-proposals/**",
                        "/api/v1/mission-matches/**",
                        "/api/v1/bookmarks",
                        "/api/v1/me",
                        "/api/v1/me/profile-image/**",
                        "/api/v1/me/change-hero",
                        "/api/v1/me/change-citizen",
                        "/api/v1/me/user-images/*",
                        "/api/v1/me/reviews/**",
                        "/api/v1/me/bookmarks",
                        "/api/v1/reviews/**",
                        "/api/v1/missions/**",
                        "/api/v1/main",
                        "/api/v1/mission-images/**",
                        "/api/v1/review-images/**",
                }, new JwtAuthInterceptor(testJwtProperties, testJwtTokenManager))
                .build();
    }

    protected abstract Object setController();

    protected String getAccessToken() {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwicm9sZSI6Ik1FTUJFUiIsImlhdCI6MTcwNjc3NTQ1MiwiZXhwIjoxNzY2Nzc1NDUyfQ.ZYrz4_2FUy9sDETyZOnRuk57UF29JUAX_sY8Evz1eKU";
    }
}
