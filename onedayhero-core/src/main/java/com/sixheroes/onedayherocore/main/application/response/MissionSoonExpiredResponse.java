package com.sixheroes.onedayherocore.main.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixheroes.onedayherocore.mission.application.response.MissionCategoryResponse;
import com.sixheroes.onedayherocore.mission.domain.repository.response.MainMissionQueryResponse;
import com.sixheroes.onedayherocore.region.application.response.RegionResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record MissionSoonExpiredResponse(
        Long id,

        String title,

        RegionResponse region,

        MissionCategoryResponse missionCategory,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate missionDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startTime,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime endTime,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime deadlineTime,

        Integer price,

        Integer bookmarkCount,

        String missionStatus,

        String imagePath,

        boolean isBookmarked
) {

    public static MissionSoonExpiredResponse from(
            MainMissionQueryResponse response,
            String imagePath
    ) {
        return MissionSoonExpiredResponse.builder()
                .id(response.getId())
                .title(response.getTitle())
                .missionCategory(MissionCategoryResponse.builder()
                        .id(response.getCategoryId())
                        .code(response.getCategoryCode())
                        .name(response.getCategoryName())
                        .build())
                .missionDate(response.getMissionDate())
                .startTime(response.getStartTime())
                .endTime(response.getEndTime())
                .deadlineTime(response.getDeadlineTime())
                .price(response.getPrice())
                .bookmarkCount(response.getBookmarkCount())
                .missionStatus(response.getMissionStatus().name())
                .region(RegionResponse.builder()
                        .id(response.getRegionId())
                        .si(response.getSi())
                        .gu(response.getGu())
                        .dong(response.getDong())
                        .build())
                .imagePath(imagePath)
                .isBookmarked(isBookmarked(response.getBookmarkId()))
                .build();
    }

    private static boolean isBookmarked(
            Long bookmarkId
    ) {
        return bookmarkId != null;
    }
}
