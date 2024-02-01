package com.sixheroes.onedayherocore.missionproposal.domain.repository.dto;

import java.util.Map;

public record MissionProposalUpdateEventDto(
        Long citizenId,
        String heroNickname,
        Long missionId,
        String missionTitle
) {

    public Map<String, Object> toMap() {
        return Map.ofEntries(
                Map.entry("heroNickname", this.heroNickname),
                Map.entry("missionId", this.missionId),
                Map.entry("missionTitle", this.missionTitle)
        );
    }
}
