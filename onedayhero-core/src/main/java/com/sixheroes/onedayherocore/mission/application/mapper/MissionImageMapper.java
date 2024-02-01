package com.sixheroes.onedayherocore.mission.application.mapper;

import com.sixheroes.onedayherocore.global.s3.dto.response.S3ImageUploadServiceResponse;
import com.sixheroes.onedayherocore.mission.domain.MissionImage;

public final class MissionImageMapper {

    private MissionImageMapper() {

    }

    public static MissionImage createMissionImage(S3ImageUploadServiceResponse response) {
        return MissionImage.createMissionImage(response.originalName(), response.uniqueName(), response.path());
    }
}
