package com.sixheroes.onedayherocore.mission.application.repository.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;
import com.sixheroes.onedayherocore.mission.domain.MissionStatus;

import java.time.LocalDate;

public record MissionCompletedQueryResponse(
        Long id,

        String title,

        Long categoryId,

        MissionCategoryCode categoryCode,

        String categoryName,

        String si,

        String gu,

        String dong,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate missionDate,

        Integer bookmarkCount,

        MissionStatus missionStatus,

        Long bookmarkId
) {
}
