package com.sixheroes.onedayherocore.notification.application.dto;

import com.sixheroes.onedayherocommon.converter.StringConverter;
import com.sixheroes.onedayherocore.notification.mongo.Alarm;
import com.sixheroes.onedayherocore.notification.mongo.AlarmTemplate;
import lombok.Builder;

import java.util.Map;

@Builder
public record AlarmPayload(
    String alarmType,
    Long userId,
    Map<String, Object> data
) {

    public Alarm toEntity(
        AlarmTemplate alarmTemplate
    ) {
        var title = StringConverter.convertMapToString(this.data, alarmTemplate.getTitle());
        var content = StringConverter.convertMapToString(this.data, alarmTemplate.getContent());

        return Alarm.builder()
            .alarmTemplate(alarmTemplate)
            .userId(this.userId)
            .title(title)
            .content(content)
            .build();
    }
}
