package com.sixheroes.onedayherocore.missionmatch.application.event;

import com.sixheroes.onedayherocore.missionmatch.application.MissionMatchReader;
import com.sixheroes.onedayherocore.missionmatch.application.event.dto.MissionMatchAction;
import com.sixheroes.onedayherocore.missionmatch.application.event.dto.MissionMatchCreateEvent;
import com.sixheroes.onedayherocore.missionmatch.application.event.dto.MissionMatchRejectEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MissionMatchEventService {

    private final MissionMatchReader missionMatchReader;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void notifyMissionMatchCreate(
        MissionMatchCreateEvent missionMatchCreateEvent
    ) {
        var missionMatchEvent = missionMatchReader.findMissionMatchEventSendCitizen(missionMatchCreateEvent.missionMatchId());

        var alarmPayload = MissionMatchEventMapper.toAlarmPayload(missionMatchEvent, MissionMatchAction.MISSION_MATCH_CREATE);

        applicationEventPublisher.publishEvent(alarmPayload);
    }

    public void notifyMissionMatchReject(
        MissionMatchRejectEvent missionMatchRejectEvent
    ) {
        var missionMatchId = missionMatchRejectEvent.missionMatchId();
        var missionMatchEventDto = missionMatchRejectEvent.isMatchedHero() ?
            missionMatchReader.findMissionMatchEventSendHero(missionMatchId) : missionMatchReader.findMissionMatchEventSendCitizen(missionMatchId);

        var alarmPayload = MissionMatchEventMapper.toAlarmPayload(missionMatchEventDto, MissionMatchAction.MISSION_MATCH_REJECT);

        applicationEventPublisher.publishEvent(alarmPayload);
    }
}
