package com.sixheroes.onedayherocore.mission.application;

import com.sixheroes.onedayherocore.IntegrationQueryDslTest;
import com.sixheroes.onedayherocore.mission.domain.*;
import com.sixheroes.onedayherocore.region.domain.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
class MissionBookmarkQueryRepositoryTest extends IntegrationQueryDslTest {

    @DisplayName("내 미션 찜목록을 조회할 수 있다.")
    @Test
    void viewMeBookmarkMissions() {
        // given
        var region = regionRepository.save(createRegion());
        var missionCategory = missionCategoryRepository.save(MissionCategory.createMissionCategory(MissionCategoryCode.MC_001));
        var bookmarkUserId = 1L;
        var citizenId = 2L;
        createFiveBookmarks(
                citizenId,
                bookmarkUserId,
                missionCategory,
                region.getId()
        );

        // when
        var pageRequest = PageRequest.of(0, 5);
        var responses = missionBookmarkQueryRepository.viewMyBookmarks(
                pageRequest,
                bookmarkUserId
        );

        // then
        assertThat(responses).hasSize(5);
    }

    private Mission createMissionWithMissionStatus(
            Long citizenId,
            MissionStatus missionStatus,
            MissionCategory missionCategory,
            Long regionId
    ) {
        var mission = Mission.builder()
                .missionStatus(missionStatus)
                .missionInfo(createMissionInfo())
                .missionCategory(missionCategory)
                .citizenId(citizenId)
                .regionId(regionId)
                .location(Mission.createPoint(1234, 5678))
                .bookmarkCount(0)
                .build();

        return missionRepository.save(mission);
    }

    private MissionInfo createMissionInfo() {
        return MissionInfo.builder()
                .title("서빙 구함")
                .missionDate(LocalDate.of(2023, 10, 10))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .deadlineTime(LocalDateTime.of(2023, 10, 1, 1, 1))
                .price(10000)
                .content("서빙 도와주기")
                .serverTime(LocalDateTime.of(
                        LocalDate.of(2023, 10, 9),
                        LocalTime.MIDNIGHT
                ))
                .build();
    }

    private void createFiveBookmarks(
            long citizenId,
            long bookmarkUserId,
            MissionCategory missionCategory,
            Long regionId
    ) {
        IntStream.range(0, 10)
                .forEach(i -> {
                    var mission = createMissionWithMissionStatus(
                            citizenId,
                            MissionStatus.MATCHING,
                            missionCategory,
                            regionId
                    );

                    if (i <= 4) {
                        var missionBookmark = MissionBookmark.builder()
                                .mission(mission)
                                .userId(bookmarkUserId)
                                .build();
                        missionBookmarkRepository.save(missionBookmark);
                        mission.addBookmarkCount();
                    }
                });
    }

    private Region createRegion() {
        return Region.builder()
                .id(1L)
                .si("서울시")
                .gu("프로구")
                .dong("래머동")
                .build();
    }
}
