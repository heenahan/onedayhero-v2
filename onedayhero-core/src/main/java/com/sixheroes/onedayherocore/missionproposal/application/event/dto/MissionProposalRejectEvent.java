package com.sixheroes.onedayherocore.missionproposal.application.event.dto;

import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;

public record MissionProposalRejectEvent(
    Long missionProposalId
) {

    public static MissionProposalRejectEvent from(
        MissionProposal missionProposal
    ) {
        return new MissionProposalRejectEvent(missionProposal.getId());
    }
}
