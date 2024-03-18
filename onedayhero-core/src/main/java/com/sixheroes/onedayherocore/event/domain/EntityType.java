package com.sixheroes.onedayherocore.event.domain;

import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityType {

    ALARM_PAYLOAD(AlarmPayload.class);

    private final Class name;
}
