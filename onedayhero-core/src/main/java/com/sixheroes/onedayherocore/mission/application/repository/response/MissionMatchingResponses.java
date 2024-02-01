package com.sixheroes.onedayherocore.mission.application.repository.response;

import com.sixheroes.onedayherocore.mission.application.response.MissionMatchingResponse;

import java.util.List;

public record MissionMatchingResponses(
    List<MissionMatchingResponse> missionMatchingResponses
) {
}
