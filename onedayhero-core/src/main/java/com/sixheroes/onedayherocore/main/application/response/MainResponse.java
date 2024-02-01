package com.sixheroes.onedayherocore.main.application.response;

import com.sixheroes.onedayherocore.mission.application.response.MissionCategoryResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MainResponse(
        List<MissionCategoryResponse> missionCategories,
        List<MissionSoonExpiredResponse> soonExpiredMissions
) {

    public static MainResponse from(
            List<MissionCategoryResponse> missionCategories,
            List<MissionSoonExpiredResponse> soonExpiredMissions
    ) {
        return MainResponse.builder()
                .missionCategories(missionCategories)
                .soonExpiredMissions(soonExpiredMissions)
                .build();
    }
}
