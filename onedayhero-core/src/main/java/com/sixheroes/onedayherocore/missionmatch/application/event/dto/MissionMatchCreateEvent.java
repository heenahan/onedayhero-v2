package com.sixheroes.onedayherocore.missionmatch.application.event.dto;


import com.sixheroes.onedayherocore.missionmatch.domain.MissionMatch;

public record MissionMatchCreateEvent(
    Long missionMatchId
) {

    public static MissionMatchCreateEvent from(
        MissionMatch missionMatch
    ) {
        return new MissionMatchCreateEvent(missionMatch.getId());
    }
}
