package com.sixheroes.onedayherocore.missionproposal.application;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.EntityNotFoundException;
import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.MissionProposalRepository;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalCreateEventDto;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalUpdateEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class MissionProposalReader {

    private final MissionProposalRepository missionProposalRepository;

    public MissionProposal findOne(
            Long missionProposalId
    ) {
        return missionProposalRepository.findById(missionProposalId)
                .orElseThrow(() -> {
                    log.debug("존재하지 않는 미션 제안입니다. id : {}", missionProposalId);
                    return new EntityNotFoundException(ErrorCode.INVALID_REQUEST_VALUE);
                });
    }

    public List<MissionProposal> findProposedMissions(
        Long heroId
    ) {
        return missionProposalRepository.findByHeroId(heroId);
    }

    public MissionProposalCreateEventDto findCreateEvent(
            Long missionProposalId
    ) {
        return missionProposalRepository.findMissionProposalCreateEventDtoById(missionProposalId)
                .orElseThrow(() -> {
                    log.debug("존재하지 않는 미션 제안입니다. id : {}", missionProposalId);
                    return new EntityNotFoundException(ErrorCode.INVALID_REQUEST_VALUE);
                });
    }

    public MissionProposalUpdateEventDto findUpdateEvent(
            Long missionProposalId
    ) {
        return missionProposalRepository.findMissionProposalUpdateEventDtoById(missionProposalId)
                .orElseThrow(() -> {
                    log.debug("존재하지 않는 미션 제안입니다. id : {}", missionProposalId);
                    return new EntityNotFoundException(ErrorCode.INVALID_REQUEST_VALUE);
                });
    }
}