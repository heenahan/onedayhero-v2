package com.sixheroes.onedayherocore.missionproposal.application.response.dto;

import com.sixheroes.onedayherocore.missionproposal.application.repository.dto.MissionProposalQueryDto;
import lombok.Builder;

@Builder
public record RegionForMissionProposalResponse(
        String si,
        String gu,
        String dong
) {

    public static RegionForMissionProposalResponse from(
            MissionProposalQueryDto missionProposalQueryDto
    ) {
        return RegionForMissionProposalResponse.builder()
                .si(missionProposalQueryDto.si())
                .gu(missionProposalQueryDto.gu())
                .dong(missionProposalQueryDto.dong())
                .build();
    }
}
