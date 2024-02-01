package com.sixheroes.onedayherocore.mission.domain.repository.dto;

import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;

public record MissionCategoryDto(
    Long userId,
    MissionCategoryCode missionCategoryCode,
    String name
) {
}
