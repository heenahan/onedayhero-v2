package com.sixheroes.onedayherocore.mission.application.request;

import lombok.Builder;

@Builder
public record MissionBookmarkCreateServiceRequest(
        Long missionId
) {
}
