package com.sixheroes.onedayherocore.mission.application;


import com.sixheroes.onedayherocore.mission.application.repository.MissionBookmarkQueryRepository;
import com.sixheroes.onedayherocore.mission.application.request.MissionBookmarkCancelServiceRequest;
import com.sixheroes.onedayherocore.mission.application.request.MissionBookmarkCreateServiceRequest;
import com.sixheroes.onedayherocore.mission.application.response.MissionBookmarkMeViewResponse;
import com.sixheroes.onedayherocore.mission.application.response.MissionBookmarkResponse;
import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.BusinessException;
import com.sixheroes.onedayherocore.mission.domain.MissionBookmark;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionBookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MissionBookmarkService {

    private final MissionBookmarkRepository missionBookmarkRepository;
    private final MissionBookmarkReader missionBookmarkReader;
    private final MissionBookmarkQueryRepository missionBookmarkQueryRepository;
    private final MissionReader missionReader;

    @Transactional(readOnly = true)
    public MissionBookmarkMeViewResponse viewMyBookmarks(
            Pageable pageable,
            Long userId
    ) {
        var responses = missionBookmarkQueryRepository.viewMyBookmarks(
                pageable,
                userId
        );

        return MissionBookmarkMeViewResponse.of(
                userId,
                responses
        );
    }

    public MissionBookmarkResponse createMissionBookmark(
            Long userId,
            MissionBookmarkCreateServiceRequest request
    ) {
        var mission = missionReader.findOne(request.missionId());
        var missionBookmark = MissionBookmark.builder()
                .mission(mission)
                .userId(userId)
                .build();

        try {
            var createdMissionBookmark = missionBookmarkRepository.save(missionBookmark);
            mission.addBookmarkCount();

            return MissionBookmarkResponse
                    .builder()
                    .id(createdMissionBookmark.getId())
                    .build();

        } catch (DataIntegrityViolationException e) {
            log.warn("이미 해당 미션에 찜을 한 상태입니다. missionId={}, userId={}",
                    request.missionId(),
                    userId
            );
            throw new BusinessException(ErrorCode.DUPLICATE_MISSION_BOOKMARK_REQUEST);
        }
    }

    public void cancelMissionBookmark(
            Long userId,
            MissionBookmarkCancelServiceRequest request
    ) {
        var mission = missionReader.findOne(request.missionId());
        var findMissionBookmark = missionBookmarkReader.findByMissionIdAndUserId(
                request.missionId(),
                userId
        );

        missionBookmarkRepository.delete(findMissionBookmark);
        mission.subBookmarkCount();
    }
}
