package com.sixheroes.onedayheroapplication.mission;

import com.sixheroes.onedayheroapplication.IntegrationApplicationTest;
import com.sixheroes.onedayheroapplication.mission.request.MissionCreateServiceRequest;
import com.sixheroes.onedayheroapplication.mission.request.MissionInfoServiceRequest;
import com.sixheroes.onedayheroapplication.mission.response.MissionCategoryResponse;
import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherodomain.bookmark.repository.UserBookMarkMissionRepository;
import com.sixheroes.onedayherodomain.mission.*;
import com.sixheroes.onedayherodomain.mission.repository.MissionCategoryRepository;
import com.sixheroes.onedayherodomain.mission.repository.MissionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MissionServiceTest extends IntegrationApplicationTest {

    @Autowired
    private UserBookMarkMissionRepository userBookMarkMissionRepository;

    @Autowired
    private MissionCategoryRepository missionCategoryRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MissionService missionService;

    @BeforeAll
    public static void setUp(@Autowired MissionCategoryRepository missionCategoryRepository) {
        var missionCategory = MissionCategory.from(MissionCategoryCode.MC_001);
        missionCategoryRepository.save(missionCategory);
    }

    @Transactional
    @DisplayName("시민은 미션을 생성 할 수 있다.")
    @Test
    void createMission() {
        // given
        var today = LocalDateTime.of(2023, 10, 10, 0, 0);

        var missionDate = LocalDate.of(2023, 10, 10);
        var startTime = LocalTime.of(10, 0);
        var endTime = LocalTime.of(10, 30);
        var deadlineTime = LocalTime.of(10, 0);

        var missionInfoServiceRequest = createMissionInfoServiceRequest(missionDate, startTime, endTime, deadlineTime);
        var missionCreateServiceRequest = createMissionCreateServiceRequest(missionInfoServiceRequest);

        // when
        var result = missionService.createMission(missionCreateServiceRequest, today);

        // then
        assertThat(result)
                .extracting(
                        "missionCategory",
                        "citizenId",
                        "regionId",
                        "location",
                        "missionInfo",
                        "bookmarkCount",
                        "missionStatus"
                )
                .containsExactly(
                        MissionCategoryResponse.builder()
                                .categoryId(missionCreateServiceRequest.missionCategoryId())
                                .code(MissionCategoryCode.MC_001.name())
                                .name(MissionCategoryCode.MC_001.getDescription())
                                .build(),
                        missionCreateServiceRequest.citizenId(),
                        missionCreateServiceRequest.regionId(),
                        new Point(missionCreateServiceRequest.longitude(), missionCreateServiceRequest.latitude()),
                        result.missionInfo(),
                        0,
                        MissionStatus.MATCHING.name()
                );
    }

    @Transactional
    @DisplayName("시민이 미션을 생성 할 때 미션의 수행 날짜가 생성 날짜보다 이전 일 수 없다.")
    @Test
    void createMissionWithMissionDateBeforeToday() {
        // given
        var today = LocalDateTime.of(2023, 10, 21, 0, 0);

        var missionDate = LocalDate.of(2023, 10, 20);
        var startTime = LocalTime.of(10, 0, 0);
        var endTime = LocalTime.of(10, 30, 0);
        var deadlineTime = LocalTime.of(10, 0, 0);

        var missionInfoServiceRequest = createMissionInfoServiceRequest(missionDate, startTime, endTime, deadlineTime);
        var missionCreateServiceRequest = createMissionCreateServiceRequest(missionInfoServiceRequest);

        // when & then
        assertThatThrownBy(() -> missionService.createMission(missionCreateServiceRequest, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.EM_003.name());
    }

    @Transactional
    @DisplayName("시민이 미션을 생성 할 때 미션의 종료 시간이 시작 시간 이전 일 수 없다.")
    @Test
    void createMissionWithEndTimeBeforeStartTime() {
        // given
        var today = LocalDateTime.of(2023, 10, 20, 0, 0);

        var missionDate = LocalDate.of(2023, 10, 20);
        var startTime = LocalTime.of(10, 0, 0);
        var endTime = LocalTime.of(9, 30, 0);
        var deadlineTime = LocalTime.of(10, 0, 0);

        var missionInfoServiceRequest = createMissionInfoServiceRequest(missionDate, startTime, endTime, deadlineTime);
        var missionCreateServiceRequest = createMissionCreateServiceRequest(missionInfoServiceRequest);

        // when & then
        assertThatThrownBy(() -> missionService.createMission(missionCreateServiceRequest, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.EM_004.name());
    }

    @Transactional
    @DisplayName("시민이 미션을 생성 할 때 미션의 마감 시간이 시작 시간 이후 일 수 없다.")
    @Test
    void createMissionWithDeadLineTimeAfterStartTime() {
        // given
        var today = LocalDateTime.of(2023, 10, 20, 0, 0);

        var missionDate = LocalDate.of(2023, 10, 20);
        var startTime = LocalTime.of(10, 0, 0);
        var endTime = LocalTime.of(10, 30, 0);
        var deadlineTime = LocalTime.of(10, 10, 0);

        var missionInfoServiceRequest = createMissionInfoServiceRequest(missionDate, startTime, endTime, deadlineTime);
        var missionCreateServiceRequest = createMissionCreateServiceRequest(missionInfoServiceRequest);

        // when & then
        assertThatThrownBy(() -> missionService.createMission(missionCreateServiceRequest, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.EM_005.name());
    }

    @Transactional
    @DisplayName("시민은 미션을 삭제 할 수 있다.")
    @EnumSource(value = MissionStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "MATCHING_COMPLETED")
    @ParameterizedTest
    void deleteMission(MissionStatus missionStatus) {
        // given
        var citizenId = 1L;
        var missionCategory = missionCategoryRepository.findById(1L).get();
        var mission = createMission(missionCategory, citizenId, missionStatus);

        var savedMission = missionRepository.save(mission);

        /*
        TODO
        userBookMarkMissionRepository.saveAll(List.of(
            createUserBookMarkMission(citizenId, savedMissionId);
        ))
        */

        // when
        missionService.deleteMission(savedMission.getId(), citizenId);

        var findMission = missionRepository.findById(savedMission.getId());
        var findUserBookMarkMission = userBookMarkMissionRepository.findByMissionId(savedMission.getId());

        // then
        assertThat(findMission).isEmpty();
        assertThat(findUserBookMarkMission).isEmpty();
    }

    @DisplayName("미션 매칭이 완료 된 상태에서는 미션을 삭제 할 수 없다.")
    @Test
    void deleteMissionWithMatchingCompleteStatus() {
        // given
        var missionCategory = missionCategoryRepository.findById(1L).get();
        var citizenId = 1L;
        var mission = createMission(missionCategory, citizenId, MissionStatus.MATCHING_COMPLETED);

        var savedMission = missionRepository.save(mission);

        // when & then
        assertThatThrownBy(() -> missionService.deleteMission(savedMission.getId(), citizenId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ErrorCode.EM_007.name());
    }

    @DisplayName("미션을 생성한 시민이 아니라면 삭제를 할 수 없다.")
    @Test
    void deleteMissionWithInValidUser() {
        // given
        var missionCategory = missionCategoryRepository.findById(1L).get();
        var citizenId = 1L;
        var mission = createMission(missionCategory, citizenId, MissionStatus.MATCHING);

        var savedMission = missionRepository.save(mission);

        var unknownCitizenId = 2L;

        // when & then
        assertThatThrownBy(() -> missionService.deleteMission(savedMission.getId(), unknownCitizenId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ErrorCode.EM_100.name());
    }


    private MissionCreateServiceRequest createMissionCreateServiceRequest(
            MissionInfoServiceRequest missionInfoServiceRequest
    ) {
        return MissionCreateServiceRequest.builder()
                .missionCategoryId(1L)
                .citizenId(1L)
                .regionId(1L)
                .latitude(1234252.23)
                .longitude(1234277.388)
                .missionInfo(missionInfoServiceRequest)
                .build();
    }

    private MissionInfoServiceRequest createMissionInfoServiceRequest(
            LocalDate missionDate,
            LocalTime startTime,
            LocalTime endTime,
            LocalTime deadlineTime
    ) {
        return MissionInfoServiceRequest
                .builder()
                .content("내용")
                .missionDate(missionDate)
                .startTime(startTime)
                .endTime(endTime)
                .deadlineTime(deadlineTime)
                .price(10000)
                .build();
    }

    private Mission createMission(MissionCategory missionCategory, Long citizenId, MissionStatus missionStatus) {
        return Mission.builder()
                .missionCategory(missionCategory)
                .missionInfo(
                        MissionInfo.builder()
                                .content("content")
                                .missionDate(LocalDate.now())
                                .startTime(LocalTime.now())
                                .endTime(LocalTime.now())
                                .deadlineTime(LocalTime.now())
                                .price(1000)
                                .build())
                .regionId(1L)
                .citizenId(citizenId)
                .location(new Point(123456.78, 123456.78))
                .bookmarkCount(0)
                .missionStatus(missionStatus)
                .build();
    }
}