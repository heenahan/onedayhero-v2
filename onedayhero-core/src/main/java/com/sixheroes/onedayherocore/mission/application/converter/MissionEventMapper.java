package com.sixheroes.onedayherocore.mission.application.converter;

import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import com.sixheroes.onedayherocore.mission.application.event.dto.MissionEventAction;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionCompletedEventQueryResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MissionEventMapper {

    public static AlarmPayload toAlarmPayload(
        MissionCompletedEventQueryResponse missionCompletedEventQueryResponse
    ) {
        return AlarmPayload.builder()
            .alarmType(MissionEventAction.MISSION_COMPLETED.name())
            .userId(missionCompletedEventQueryResponse.heroId())
            .data(missionCompletedEventQueryResponse.toMap())
            .build();
    }
}
