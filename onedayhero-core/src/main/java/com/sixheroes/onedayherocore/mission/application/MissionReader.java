package com.sixheroes.onedayherocore.mission.application;

import com.sixheroes.onedayherocore.mission.application.repository.MissionQueryRepository;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionCompletedEventQueryResponse;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionQueryResponse;
import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.EntityNotFoundException;
import com.sixheroes.onedayherocore.mission.domain.Mission;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class MissionReader {

    private final MissionRepository missionRepository;
    private final MissionQueryRepository missionQueryRepository;

    public Mission findOne(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> {
                    log.debug("존재하지 않는 미션 아이디가 입력되었습니다. id : {}", missionId);
                    return new EntityNotFoundException(ErrorCode.INVALID_REQUEST_VALUE);
                });
    }

    public MissionQueryResponse fetchFindOne(
            Long missionId,
            Long userId
    ) {
        return missionQueryRepository.fetchOne(missionId, userId)
                .orElseThrow(() -> {
                    log.debug("존재하지 않는 미션 아이디가 입력되었습니다. id : {}", missionId);
                    return new EntityNotFoundException(ErrorCode.INVALID_REQUEST_VALUE);
                });
    }

    public MissionCompletedEventQueryResponse findMissionCompletedEvent(
        Long missionId
    ) {
        return missionQueryRepository.findMissionCompletedEvent(missionId)
            .orElseThrow(() -> {
                log.debug("존재하지 않는 미션 아이디가 입력되었습니다. id : {}", missionId);
                return new EntityNotFoundException(ErrorCode.INVALID_REQUEST_VALUE);
            });
    }
}
