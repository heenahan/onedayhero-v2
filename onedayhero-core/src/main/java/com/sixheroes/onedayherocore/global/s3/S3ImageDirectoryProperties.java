package com.sixheroes.onedayherocore.global.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "s3-image")
public class S3ImageDirectoryProperties {

    private final String profileDir;
    private final String missionDir;
    private final String reviewDir;
}
