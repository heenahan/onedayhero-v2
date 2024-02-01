package com.sixheroes.onedayherocore.missionproposal.application;

import com.sixheroes.onedayherocore.mission.application.MissionImageReader;
import com.sixheroes.onedayherocore.mission.application.MissionReader;
import com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalApproveEvent;
import com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalCreateEvent;
import com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalRejectEvent;
import com.sixheroes.onedayherocore.missionproposal.application.repository.MissionProposalQueryRepository;
import com.sixheroes.onedayherocore.missionproposal.application.repository.dto.MissionProposalQueryDto;
import com.sixheroes.onedayherocore.missionproposal.application.request.MissionProposalCreateServiceRequest;
import com.sixheroes.onedayherocore.missionproposal.application.response.MissionProposalIdResponse;
import com.sixheroes.onedayherocore.missionproposal.application.response.dto.MissionProposalResponse;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.MissionProposalRepository;
import com.sixheroes.onedayherocore.user.application.reader.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MissionProposalService {

    private final MissionProposalRepository missionProposalRepository;
    private final MissionProposalQueryRepository missionProposalQueryRepository;

    private final MissionReader missionReader;
    private final MissionProposalReader missionProposalReader;
    private final MissionImageReader missionImageReader;
    private final UserReader userReader;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public MissionProposalIdResponse createMissionProposal(
            Long userId,
            MissionProposalCreateServiceRequest request
    ) {
        validMission(request.missionId(), userId);
        validHero(request.heroId());

        var missionProposal = request.toEntity();
        var savedMissionProposal = missionProposalRepository.save(missionProposal);

        var missionProposalEvent = MissionProposalCreateEvent.from(missionProposal);
        applicationEventPublisher.publishEvent(missionProposalEvent);

        return MissionProposalIdResponse.from(savedMissionProposal);
    }

    @Transactional
    public MissionProposalIdResponse approveMissionProposal(
            Long userId,
            Long missionProposalId
    ) {
        var missionProposal = missionProposalReader.findOne(missionProposalId);

        var missionId = missionProposal.getMissionId();
        validMission(missionId);

        missionProposal.changeMissionProposalStatusApprove(userId);

        var missionProposalApproveEvent = MissionProposalApproveEvent.from(missionProposal);
        applicationEventPublisher.publishEvent(missionProposalApproveEvent);

        return MissionProposalIdResponse.from(missionProposal);
    }

    @Transactional
    public MissionProposalIdResponse rejectMissionProposal(
            Long userId,
            Long missionProposalId
    ) {
        var missionProposal = missionProposalReader.findOne(missionProposalId);

        var missionId = missionProposal.getMissionId();
        validMission(missionId);

        missionProposal.changeMissionProposalStatusReject(userId);

        var missionProposalRejectEvent = MissionProposalRejectEvent.from(missionProposal);
        applicationEventPublisher.publishEvent(missionProposalRejectEvent);

        return MissionProposalIdResponse.from(missionProposal);
    }

    public Slice<MissionProposalResponse> findMissionProposal(
            Long heroId,
            Pageable pageable
    ) {
        var slice = missionProposalQueryRepository.findByHeroIdAndPageable(heroId, pageable);

        return createMissionProposalResponseWithImage(slice);
    }

    private Slice<MissionProposalResponse> createMissionProposalResponseWithImage(
            Slice<MissionProposalQueryDto> slice
    ) {
        var missionIds = slice.getContent()
                .stream()
                .map(MissionProposalQueryDto::missionId)
                .toList();
        var missionImageByMissionId = missionImageReader.findMissionImageByMissionId(missionIds);

        return slice.map(queryResult -> {
            var missionId = queryResult.missionId();
            var missionImagePath = Optional.ofNullable(missionImageByMissionId.get(missionId))
                    .filter(Predicate.not(List::isEmpty))
                    .map(list -> list.get(0).path())
                    .orElse(null);
            var isBookmarked = Objects.nonNull(queryResult.bookmarkId());

            return MissionProposalResponse.from(queryResult, missionImagePath, isBookmarked);
        });
    }

    private void validMission(
            Long missionId,
            Long userId
    ) {
        var mission = missionReader.findOne(missionId);

        mission.validMissionProposalPossible(userId);
    }

    private void validMission(
            Long missionId
    ) {
        var mission = missionReader.findOne(missionId);

        mission.validMissionProposalChangeStatus();
    }

    private void validHero(
            Long heroId
    ) {
        var hero = userReader.findOne(heroId);

        hero.validPossibleMissionProposal();
    }
}
