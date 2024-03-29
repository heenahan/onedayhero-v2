package com.sixheroes.onedayherocore.mission.application.repository.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixheroes.onedayherocore.mission.domain.MissionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record MissionBookmarkMeQueryResponse(
        Long missionId,

        Long missionBookmarkId,

        MissionStatus missionStatus,

        String title,

        Integer bookmarkCount,

        Integer price,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate missionDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startTime,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime endTime,

        String categoryName,

        Long regionId,

        String si,

        String gu,

        String dong
) {
}
