package com.sixheroes.onedayherocore.notification.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixheroes.onedayherocore.notification.mongo.Alarm;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AlarmResponse(
    String id,

    String title,

    String content,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt
) {

    public static AlarmResponse from(
        Alarm alarm
    ) {
        return AlarmResponse.builder()
            .id(alarm.getId())
            .title(alarm.getTitle())
            .content(alarm.getContent())
            .createdAt(alarm.getCreatedAt())
            .build();
    }
}
