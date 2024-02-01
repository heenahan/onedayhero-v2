package com.sixheroes.onedayherocore.mission.application.response;

import com.sixheroes.onedayherocore.mission.domain.MissionImage;
import lombok.Builder;

@Builder
public record MissionImageResponse(
        Long id,
        String path
) {

    public static MissionImageResponse from(
            Long id,
            String path
    ) {
        return MissionImageResponse.builder()
                .id(id)
                .path(path)
                .build();
    }

    public static MissionImageResponse from(
            MissionImage missionImage
    ) {
        return MissionImageResponse.builder()
                .id(missionImage.getId())
                .path(missionImage.getPath())
                .build();
    }
}
