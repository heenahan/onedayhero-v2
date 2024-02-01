package com.sixheroes.onedayherocore.mission.application.event.dto;

import com.sixheroes.onedayherocore.mission.domain.Mission;

public record MissionCompletedEvent(
    Long missionId
) {

    public static MissionCompletedEvent from(
        Mission mission
    ) {
        return new MissionCompletedEvent(mission.getId());
    }
}
