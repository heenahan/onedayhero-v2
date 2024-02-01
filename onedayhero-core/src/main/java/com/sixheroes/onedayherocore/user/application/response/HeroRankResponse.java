package com.sixheroes.onedayherocore.user.application.response;

import com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionCategoryDto;
import com.sixheroes.onedayherocore.user.application.repository.dto.HeroRankQueryResponse;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Builder
public record HeroRankResponse(
    Long id,
    String nickname,
    Integer heroScore,
    String profileImagePath,
    Integer rank,

    List<MissionCategoryResponse> favoriteMissionCategories
) {

    public static HeroRankResponse of(
        HeroRankQueryResponse heroRankQueryResponse,
        List<MissionCategoryDto> missionCategories,
        Pageable pageable,
        int index
    ) {
        int rank = pageable.getPageNumber() * pageable.getPageSize() + index + 1;
        var missionCategoryResponses = missionCategories.stream()
            .map(MissionCategoryResponse::from)
            .toList();

        return HeroRankResponse.builder()
            .id(heroRankQueryResponse.userId())
            .nickname(heroRankQueryResponse.nickname())
            .heroScore(heroRankQueryResponse.heroScore())
            .profileImagePath(heroRankQueryResponse.profileImagePath())
            .rank(rank)
            .favoriteMissionCategories(missionCategoryResponses)
            .build();
    }

    public record MissionCategoryResponse(
        String code,
        String name
    ) {

        static MissionCategoryResponse from(
            MissionCategoryDto missionCategory
        ) {
            return new MissionCategoryResponse(
                missionCategory.missionCategoryCode().name(),
                missionCategory.name()
            );
        }
    }
}
