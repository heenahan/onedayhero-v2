package com.sixheroes.onedayherocore.missionproposal.application.event.dto;

import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;
import lombok.Builder;

@Builder
public record MissionProposalCreateEvent(
    Long missionProposalId
) {

    public static MissionProposalCreateEvent from(
        MissionProposal missionProposal
    ) {
        return new MissionProposalCreateEvent(missionProposal.getId());
    }
}
