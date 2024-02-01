package com.sixheroes.onedayherocore.mission.application.request;

import lombok.Builder;

@Builder
public record MissionBookmarkCancelServiceRequest(
        Long missionId
) {
}
