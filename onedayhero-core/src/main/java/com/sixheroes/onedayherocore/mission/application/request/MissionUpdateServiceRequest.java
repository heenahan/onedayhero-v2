package com.sixheroes.onedayherocore.mission.application.request;


import com.sixheroes.onedayherocore.global.s3.dto.request.S3ImageUploadServiceRequest;
import com.sixheroes.onedayherocore.mission.domain.Mission;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MissionUpdateServiceRequest(
        Long missionCategoryId,
        String regionName,
        Long userId,
        Double latitude,
        Double longitude,
        MissionInfoServiceRequest missionInfo,
        List<S3ImageUploadServiceRequest> imageFiles
) {

    public Mission toEntity(
            MissionCategory missionCategory,
            LocalDateTime serverTime,
            Long regionId
    ) {
        return Mission.builder()
                .missionCategory(missionCategory)
                .regionId(regionId)
                .location(Mission.createPoint(longitude, latitude))
                .missionInfo(missionInfo.toVo(serverTime))
                .build();
    }
}
