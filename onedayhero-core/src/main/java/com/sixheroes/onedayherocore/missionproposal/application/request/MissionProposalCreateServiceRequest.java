package com.sixheroes.onedayherocore.missionproposal.application.request;

import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;
import lombok.Builder;

@Builder
public record MissionProposalCreateServiceRequest(
    Long missionId,
    Long heroId
) {
    public MissionProposal toEntity() {
        return MissionProposal.builder()
            .missionId(missionId)
            .heroId(heroId)
            .build();
    }
}
