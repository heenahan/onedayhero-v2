package com.sixheroes.onedayherocore.mission.application.event.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MissionEventAction {
    MISSION_COMPLETED("미션 완료");

    private final String description;
}
