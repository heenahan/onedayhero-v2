package com.sixheroes.onedayherocore.missionproposal.application.event.dto;

import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;

public record MissionProposalApproveEvent(
    Long missionProposalId
) {

    public static MissionProposalApproveEvent from(
        MissionProposal missionProposal
    ) {
        return new MissionProposalApproveEvent(missionProposal.getId());
    }
}
