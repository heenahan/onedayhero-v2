package com.sixheroes.onedayherocore.missionproposal.application.response.dto;

import com.sixheroes.onedayherocore.missionproposal.application.repository.dto.MissionProposalQueryDto;
public record MissionProposalResponse(
        Long id,
        MissionForMissionProposalResponse mission
) {

    public static MissionProposalResponse from(
            MissionProposalQueryDto missionProposalQueryDto,
            String imagePath,
            Boolean isBookMarked
    ) {
        var mission = MissionForMissionProposalResponse.from(missionProposalQueryDto, imagePath, isBookMarked);

        return new MissionProposalResponse(missionProposalQueryDto.missionProposalId(), mission);
    }
}
