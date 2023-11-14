package com.sixheroes.onedayheroapi.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.sixheroes.onedayheroapi.global.argumentsresolver.authuser.AuthUserArgumentResolver;
import com.sixheroes.onedayheroapi.global.handler.GlobalExceptionHandler;
import com.sixheroes.onedayheroapi.global.interceptor.JwtAuthInterceptor;
import com.sixheroes.onedayheroapplication.global.jwt.JwtProperties;
import com.sixheroes.onedayheroapplication.global.jwt.JwtTokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@ExtendWith(RestDocumentationExtension.class)
@Import(JwtTestConfiguration.class)
public abstract class RestDocsSupport {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtTokenManager jwtTokenManager;
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
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(), new AuthUserArgumentResolver(jwtProperties))
                // @AuthUser Long userId 적용된 컨트롤러만 인터셉터 임시 적용
                .addMappedInterceptors(new String[]{
                        "/api/v1/me/reviews/*",
                        "/api/v1/me/bookmarks"
                }, new JwtAuthInterceptor(jwtProperties, jwtTokenManager))
                .build();
    }

    protected abstract Object setController();

    protected String getAccessToken() {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwicm9sZSI6Ik1FTUJFUiIsImlhdCI6MTY5OTg2MTcxNCwiZXhwIjoxNjk5OTIxNzE0fQ.v_xYMcOc4WIno8rIgUTfyCRjyT1vQrsMGcZVrLASyEI";
    }
}
