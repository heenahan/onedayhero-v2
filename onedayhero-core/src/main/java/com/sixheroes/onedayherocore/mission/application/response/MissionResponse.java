package com.sixheroes.onedayherocore.mission.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionQueryResponse;
import com.sixheroes.onedayherocore.mission.domain.Mission;
import com.sixheroes.onedayherocore.mission.domain.MissionImage;
import com.sixheroes.onedayherocore.mission.domain.MissionInfo;
import com.sixheroes.onedayherocore.region.application.response.RegionResponse;
import com.sixheroes.onedayherocore.region.domain.Region;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
public record MissionResponse(
        Long id,
        MissionCategoryResponse missionCategory,
        Long citizenId,
        RegionResponse region,
        Double longitude,
        Double latitude,
        MissionInfoResponse missionInfo,
        Integer bookmarkCount,
        String missionStatus,
        List<MissionImageResponse> missionImage,
        boolean isBookmarked
) {

    public static MissionResponse from(
            MissionQueryResponse response,
            List<MissionImage> missionImages,
            boolean isBookmarked
    ) {
        return MissionResponse.builder()
                .id(response.id())
                .missionCategory(
                        MissionCategoryResponse.from(response)
                )
                .citizenId(response.citizenId())
                .region(
                        RegionResponse.from(response)
                )
                .longitude(response.location().getX())
                .latitude(response.location().getY())
                .missionInfo(
                        MissionInfoResponse.from(response)
                )
                .bookmarkCount(response.bookmarkCount())
                .missionStatus(response.missionStatus().name())
                .missionImage(missionImages.stream()
                        .map(MissionImageResponse::from)
                        .toList())
                .isBookmarked(isBookmarked)
                .build();
    }

    public static MissionResponse from(
            Mission mission,
            Region region,
            List<MissionImage> missionImages
    ) {
        return MissionResponse.builder()
                .id(mission.getId())
                .missionCategory(
                        MissionCategoryResponse.from(mission.getMissionCategory())
                )
                .citizenId(mission.getCitizenId())
                .region(
                        RegionResponse.from(region)
                )
                .longitude(mission.getLocation().getX())
                .latitude(mission.getLocation().getY())
                .missionInfo(MissionInfoResponse.from(mission.getMissionInfo()))
                .bookmarkCount(mission.getBookmarkCount())
                .missionStatus(mission.getMissionStatus().name())
                .missionImage(missionImages.stream()
                        .map(MissionImageResponse::from)
                        .toList())
                .build();
    }

    @Builder
    public record MissionInfoResponse(
            String title,

            String content,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
            LocalDate missionDate,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
            LocalTime startTime,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
            LocalTime endTime,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            LocalDateTime deadlineTime,

            Integer price
    ) {

        public static MissionInfoResponse from(
                MissionQueryResponse response
        ) {
            return MissionInfoResponse.builder()
                    .title(response.title())
                    .content(response.content())
                    .missionDate(response.missionDate())
                    .startTime(response.startTime())
                    .endTime(response.endTime())
                    .deadlineTime(response.deadlineTime())
                    .price(response.price())
                    .build();
        }

        public static MissionInfoResponse from(
                MissionInfo missionInfo
        ) {
            return MissionInfoResponse.builder()
                    .title(missionInfo.getTitle())
                    .content(missionInfo.getContent())
                    .missionDate(missionInfo.getMissionDate())
                    .startTime(missionInfo.getStartTime())
                    .endTime(missionInfo.getEndTime())
                    .deadlineTime(missionInfo.getDeadlineTime())
                    .price(missionInfo.getPrice())
                    .build();
        }
    }
}
