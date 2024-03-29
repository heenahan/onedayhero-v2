package com.sixheroes.onedayherocore.missionmatch.application.request;

import lombok.Builder;

@Builder
public record MissionMatchCreateServiceRequest(
        Long missionId,
        Long heroId
) {
}
