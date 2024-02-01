package com.sixheroes.onedayherocore.mission.application;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.EntityNotFoundException;
import com.sixheroes.onedayherocore.mission.domain.MissionImage;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionImageRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionImageQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class MissionImageReader {

    private final MissionImageRepository missionImageRepository;

    public Map<Long, List<MissionImageQueryResponse>> findMissionImageByMissionId(
            List<Long> missionId
    ) {
        var missionImages = missionImageRepository.findByMissionIdIn(missionId);

        return missionImages.stream()
                .collect(Collectors.groupingBy(MissionImageQueryResponse::missionId));
    }

    public MissionImage findById(
            Long missionImageId
    ) {
        return missionImageRepository.findById(missionImageId)
                .orElseThrow(() -> {
                    log.warn("존재하지 않는 미션 이미지입니다. missionImageId : {}", missionImageId);
                    return new EntityNotFoundException(ErrorCode.NOT_FOUND_IMAGE);
                });
    }
}
