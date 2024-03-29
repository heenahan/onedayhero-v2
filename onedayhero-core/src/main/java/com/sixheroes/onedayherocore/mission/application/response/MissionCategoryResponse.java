package com.sixheroes.onedayherocore.mission.application.response;

import com.sixheroes.onedayherocore.mission.application.repository.response.MissionCompletedQueryResponse;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionMatchingQueryResponse;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionProgressQueryResponse;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionQueryResponse;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import lombok.Builder;

@Builder
public record MissionCategoryResponse(
        Long id,
        String code,
        String name
) {
    public static MissionCategoryResponse from(
            MissionQueryResponse response
    ) {
        return MissionCategoryResponse.builder()
                .id(response.categoryId())
                .code(response.categoryCode().name())
                .name(response.categoryName())
                .build();
    }

    public static MissionCategoryResponse from(
            MissionProgressQueryResponse response
    ) {
        return MissionCategoryResponse.builder()
                .id(response.categoryId())
                .code(response.categoryCode().name())
                .name(response.categoryName())
                .build();
    }

    public static MissionCategoryResponse from(
            MissionCompletedQueryResponse response
    ) {
        return MissionCategoryResponse.builder()
                .id(response.categoryId())
                .code(response.categoryCode().name())
                .name(response.categoryName())
                .build();
    }

    public static MissionCategoryResponse from(
        MissionMatchingQueryResponse response
    ) {
        return MissionCategoryResponse.builder()
            .id(response.categoryId())
            .code(response.categoryCode().name())
            .name(response.categoryName())
            .build();
    }


    public static MissionCategoryResponse from(
            MissionCategory missionCategory
    ) {
        return MissionCategoryResponse.builder()
                .id(missionCategory.getId())
                .code(missionCategory.getMissionCategoryCode().name())
                .name(missionCategory.getName())
                .build();
    }
}
