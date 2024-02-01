package com.sixheroes.onedayherocore.missionproposal.application.response;

import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;

public record MissionProposalIdResponse(
    Long id
) {

    public static MissionProposalIdResponse from(
        MissionProposal missionProposal
    ) {
        return new MissionProposalIdResponse(missionProposal.getId());
    }
}
