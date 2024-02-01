package com.sixheroes.onedayherocore.missionmatch.application.request;

import lombok.Builder;

@Builder
public record MissionMatchCancelServiceRequest(
        Long missionId
) {
}
