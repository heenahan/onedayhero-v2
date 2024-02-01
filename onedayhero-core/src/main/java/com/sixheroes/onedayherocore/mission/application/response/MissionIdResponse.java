package com.sixheroes.onedayherocore.mission.application.response;

public record MissionIdResponse(
        Long id
) {

    public static MissionIdResponse from(
            Long id
    ) {
        return new MissionIdResponse(id);
    }
}
