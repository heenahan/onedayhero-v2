package com.sixheroes.onedayherocore.missionproposal.application.event;

import com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalAction;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalCreateEventDto;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalUpdateEventDto;
import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalAction.MISSION_PROPOSAL_CREATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MissionProposalEventMapper {

    public static AlarmPayload toAlarmPayload(
        MissionProposalCreateEventDto missionProposalCreateEventDto
    ) {
        return AlarmPayload.builder()
            .alarmType(MISSION_PROPOSAL_CREATE.name())
            .userId(missionProposalCreateEventDto.heroId())
            .data(missionProposalCreateEventDto.toMap())
            .build();
    }

    public static AlarmPayload toAlarmPayload(
            MissionProposalUpdateEventDto missionProposalUpdateEventDto,
            MissionProposalAction missionProposalAction
    ) {
        return AlarmPayload.builder()
                .alarmType(missionProposalAction.name())
                .userId(missionProposalUpdateEventDto.citizenId())
                .data(missionProposalUpdateEventDto.toMap())
                .build();
    }
}
