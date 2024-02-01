package com.sixheroes.onedayherocore.user.application.response;

import com.sixheroes.onedayherocore.region.application.response.RegionResponse;
import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.user.domain.User;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
    UserBasicInfoResponse basicInfo,
    UserImageResponse image,
    UserFavoriteWorkingDayResponse favoriteWorkingDay,
    List<RegionResponse> favoriteRegions,
    Integer heroScore,
    Boolean isHeroMode
) {

    public static UserResponse from(
        User user,
        List<Region> regions
    ) {
        var userBasicInfoResponse = UserBasicInfoResponse.from(user.getUserBasicInfo());
        var userImageResponse = user.getUserImages().stream()
            .map(UserImageResponse::from)
            .findFirst()
            .orElseGet(UserImageResponse::empty);
        var userFavoriteWorkingDayResponse = UserFavoriteWorkingDayResponse.from(user.getUserFavoriteWorkingDay());
        var favoriteRegions = regions.stream()
            .map(RegionResponse::from)
            .toList();

        return UserResponse.builder()
            .basicInfo(userBasicInfoResponse)
            .image(userImageResponse)
            .favoriteWorkingDay(userFavoriteWorkingDayResponse)
            .favoriteRegions(favoriteRegions)
            .heroScore(user.getHeroScore())
            .isHeroMode(user.getIsHeroMode())
            .build();
    }
}
