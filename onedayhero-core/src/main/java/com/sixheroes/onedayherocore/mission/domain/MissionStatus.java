package com.sixheroes.onedayherocore.mission.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionStatus {
    MATCHING("매칭 중", 1),
    MATCHING_COMPLETED("매칭 완료", 2),
    MISSION_COMPLETED("미션 완료", 3),
    EXPIRED("마감된 미션", 4);

    private final String description;
    private final Integer priorityStatus;

    public boolean isMatching() {
        return this == MATCHING;
    }

    public boolean isMatchingCompleted() {
        return this == MATCHING_COMPLETED;
    }

    public boolean isExpired() {
        return this == EXPIRED;
    }
}
