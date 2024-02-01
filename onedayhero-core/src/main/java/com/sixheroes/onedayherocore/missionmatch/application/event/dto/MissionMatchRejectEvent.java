package com.sixheroes.onedayherocore.missionmatch.application.event.dto;

import com.sixheroes.onedayherocore.missionmatch.domain.MissionMatch;

public record MissionMatchRejectEvent(
    Long userId, // 보내는 사람
    Long missionMatchId,
    boolean isMatchedHero
) {

    public static MissionMatchRejectEvent from(
        Long userId,
        MissionMatch missionMatch
    ) {
        var matchedHero = missionMatch.isMatchedHero(userId);
        return new MissionMatchRejectEvent(userId, missionMatch.getId(), matchedHero);
    }
}
