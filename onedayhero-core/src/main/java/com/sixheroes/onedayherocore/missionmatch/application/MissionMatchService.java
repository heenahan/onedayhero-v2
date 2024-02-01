package com.sixheroes.onedayherocore.missionmatch.application;

import com.sixheroes.onedayherocore.mission.application.MissionReader;
import com.sixheroes.onedayherocore.missionmatch.application.event.dto.MissionMatchCreateEvent;
import com.sixheroes.onedayherocore.missionmatch.application.event.dto.MissionMatchRejectEvent;
import com.sixheroes.onedayherocore.missionmatch.application.request.MissionMatchCreateServiceRequest;
import com.sixheroes.onedayherocore.missionmatch.application.request.MissionMatchCancelServiceRequest;
import com.sixheroes.onedayherocore.missionmatch.application.response.MissionMatchResponse;
import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.BusinessException;
import com.sixheroes.onedayherocore.missionmatch.domain.MissionMatch;
import com.sixheroes.onedayherocore.missionmatch.domain.repository.MissionMatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MissionMatchService {

    private final MissionMatchRepository missionMatchRepository;
    private final MissionReader missionReader;
    private final MissionMatchReader missionMatchReader;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MissionMatchResponse createMissionMatch(
            Long userId,
            MissionMatchCreateServiceRequest request
    ) {
        var mission = missionReader.findOne(request.missionId());
        var missionMatch = MissionMatch.createMissionMatch(
                mission.getId(),
                request.heroId()
        );

        mission.completeMissionMatching(userId);
        var savedMissionMatch = missionMatchRepository.save(missionMatch);

        var missionMatchCreateEvent = MissionMatchCreateEvent.from(missionMatch);
        applicationEventPublisher.publishEvent(missionMatchCreateEvent);

        return MissionMatchResponse
                .builder()
                .id(savedMissionMatch.getId())
                .build();
    }

    public MissionMatchResponse cancelMissionMatch(
            Long userId,
            MissionMatchCancelServiceRequest request
    ) {
        var mission = missionReader.findOne(request.missionId());
        var missionMatch = missionMatchReader.findByMissionIdAndMatched(mission.getId());

        validateMissionMatchCancelRequestUser(userId, mission.getCitizenId(), missionMatch.getHeroId());
        mission.cancelMissionMatching(mission.getCitizenId());

        missionMatch.canceled();

        var missionMatchRejectEvent = MissionMatchRejectEvent.from(userId, missionMatch);
        applicationEventPublisher.publishEvent(missionMatchRejectEvent);

        return MissionMatchResponse.builder()
                .id(missionMatch.getId())
                .build();
    }

    private void validateMissionMatchCancelRequestUser(
            Long userId,
            Long citizenId,
            Long heroId
    ) {
       if (!(Objects.equals(userId, citizenId) || Objects.equals(userId, heroId))) {
           throw new BusinessException(ErrorCode.UNAUTHORIZED_REQUEST);
        }
    }
}
