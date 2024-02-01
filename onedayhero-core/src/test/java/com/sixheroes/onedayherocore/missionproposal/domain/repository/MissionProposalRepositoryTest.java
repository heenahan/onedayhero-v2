package com.sixheroes.onedayherocore.missionproposal.domain.repository;

import com.sixheroes.onedayherocore.IntegrationRepositoryTest;
import com.sixheroes.onedayherocore.mission.domain.*;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionCategoryRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionRepository;
import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;
import com.sixheroes.onedayherocore.user.domain.*;
import com.sixheroes.onedayherocore.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class MissionProposalRepositoryTest extends IntegrationRepositoryTest {

    @DisplayName("미션 제안 아이드를 통해 미션 제안 생성 이벤트를 찾는다.")
    @Test
    void findMissionProposalCreateEventDtoById() {
        // given
        var citizen = createUser();
        userRepository.save(citizen);

        var missionCategory = createMissionCategory();
        missionCategoryRepository.save(missionCategory);

        var mission = createMission(missionCategory, citizen.getId());
        missionRepository.save(mission);

        var missionProposal = createMissionProposal(mission.getId());
        missionProposalRepository.save(missionProposal);

        // when
        var missionProposalCreateEventDto = missionProposalRepository.findMissionProposalCreateEventDtoById(missionProposal.getId());

        // then
        assertThat(missionProposalCreateEventDto).isNotEmpty();
        assertThat(missionProposalCreateEventDto.get())
                .extracting("heroId", "citizenNickname", "missionId", "missionTitle")
                .containsExactly(
                        missionProposal.getHeroId(),
                        citizen.getUserBasicInfo().getNickname(),
                        mission.getId(),
                        mission.getMissionInfo().getTitle()
                );
    }

    public MissionProposal createMissionProposal(
            Long missionId
    ) {
        return MissionProposal.builder()
                .heroId(2L)
                .missionId(missionId)
                .build();
    }

    private MissionCategory createMissionCategory() {
        return MissionCategory.builder()
                .id(MissionCategoryCode.MC_001.getCategoryId())
                .missionCategoryCode(MissionCategoryCode.MC_001)
                .name(MissionCategoryCode.MC_001.getDescription())
                .build();
    }

    private Mission createMission(
            MissionCategory missionCategory,
            Long citizenId
    ) {
        return Mission.builder()
                .missionCategory(missionCategory)
                .missionInfo(
                        MissionInfo.builder()
                                .title("title")
                                .content("content")
                                .missionDate(LocalDate.of(2023, 11, 1))
                                .startTime(LocalTime.of(12, 30))
                                .endTime(LocalTime.of(14, 30))
                                .deadlineTime(LocalDateTime.of(
                                        LocalDate.of(2023, 11, 1),
                                        LocalTime.of(12, 0)
                                ))
                                .price(1000)
                                .serverTime(LocalDateTime.of(
                                        LocalDate.of(2023, 10, 31),
                                        LocalTime.MIDNIGHT
                                ))
                                .build())
                .regionId(1L)
                .citizenId(citizenId)
                .location(Mission.createPoint(123456.78, 123456.78))
                .missionStatus(MissionStatus.MATCHING)
                .bookmarkCount(0)
                .build();
    }

    private User createUser() {
        return User.builder()
                .userBasicInfo(createUserBasicInfo())
                .userFavoriteWorkingDay(createUserFavoriteWorkingDay())
                .userSocialType(UserSocialType.KAKAO)
                .userRole(UserRole.MEMBER)
                .email(createEmail())
                .build();
    }

    private UserBasicInfo createUserBasicInfo() {
        return UserBasicInfo.builder()
                .nickname("이름")
                .birth(LocalDate.of(1990, 1, 1))
                .gender(UserGender.MALE)
                .introduce("자기소개")
                .build();
    }

    private UserFavoriteWorkingDay createUserFavoriteWorkingDay() {
        return UserFavoriteWorkingDay.builder()
                .favoriteDate(List.of(Week.MON, Week.THU))
                .favoriteStartTime(LocalTime.of(12, 0, 0))
                .favoriteEndTime(LocalTime.of(18, 0, 0))
                .build();
    }

    private Email createEmail() {
        return Email.builder()
                .email("abc@123.com")
                .build();
    }
}