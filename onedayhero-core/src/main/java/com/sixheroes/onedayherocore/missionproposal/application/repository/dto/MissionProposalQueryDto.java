package com.sixheroes.onedayherocore.missionproposal.application.repository.dto;

import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;
import com.sixheroes.onedayherocore.mission.domain.MissionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record MissionProposalQueryDto(
    Long missionProposalId,
    Long missionId,
    Long citizenId,
    String title,
    Integer price,
    LocalDate missionDate,
    LocalTime startTime,
    LocalTime endTime,
    LocalDateTime missionCreatedAt,
    Long categoryId,
    MissionCategoryCode categoryCode,
    String categoryName,
    String si,
    String gu,
    String dong,
    Integer bookmarkCount,
    MissionStatus missionStatus,
    Long bookmarkId,
    LocalDateTime missionProposalCreatedAt
) {
}
