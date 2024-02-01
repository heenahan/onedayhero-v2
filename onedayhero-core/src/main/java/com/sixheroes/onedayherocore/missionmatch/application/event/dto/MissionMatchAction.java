package com.sixheroes.onedayherocore.missionmatch.application.event.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MissionMatchAction {
    MISSION_MATCH_CREATE("미션 매칭 생성"),
    MISSION_MATCH_REJECT("미션 매칭 거절");

    private final String description;
}