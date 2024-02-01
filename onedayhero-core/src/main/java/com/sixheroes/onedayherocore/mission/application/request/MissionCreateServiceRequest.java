package com.sixheroes.onedayherocore.mission.application.request;

import com.sixheroes.onedayherocore.global.s3.dto.request.S3ImageUploadServiceRequest;
import com.sixheroes.onedayherocore.mission.domain.Mission;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MissionCreateServiceRequest(
        Long missionCategoryId,
        Long citizenId,
        String regionName,
        Double latitude,
        Double longitude,
        List<S3ImageUploadServiceRequest> imageFiles,
        MissionInfoServiceRequest missionInfo
) {

    public Mission toEntity(
            MissionCategory missionCategory,
            Long regionId,
            LocalDateTime serverTime
    ) {
        return Mission.createMission(
                missionCategory,
                citizenId,
                regionId,
                longitude,
                latitude,
                missionInfo.toVo(serverTime)
        );
    }
}
