package com.sixheroes.onedayherocore.missionmatch.application.event;


import com.sixheroes.onedayherocore.missionmatch.domain.repository.dto.MissionMatchEventDto;
import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import com.sixheroes.onedayherocore.missionmatch.application.event.dto.MissionMatchAction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MissionMatchEventMapper {

    public static AlarmPayload toAlarmPayload(
        MissionMatchEventDto missionMatchEventDto,
        MissionMatchAction missionMatchAction
    ) {
        return AlarmPayload.builder()
            .alarmType(missionMatchAction.name())
            .userId(missionMatchEventDto.receiverId())
            .data(missionMatchEventDto.toMap())
            .build();
    }
}
