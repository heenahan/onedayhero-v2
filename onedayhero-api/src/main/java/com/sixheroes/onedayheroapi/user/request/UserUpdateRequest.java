package com.sixheroes.onedayheroapi.user.request;

import com.sixheroes.onedayherocore.user.application.request.UserBasicInfoServiceRequest;
import com.sixheroes.onedayherocore.user.application.request.UserFavoriteWorkingDayServiceRequest;
import com.sixheroes.onedayherocore.user.application.request.UserServiceUpdateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserUpdateRequest(
        @Valid
        UserBasicInfoRequest basicInfo,

        @Valid
        UserFavoriteWorkingDayRequest favoriteWorkingDay,

        @NotNull(message = "유저 선호 지역은 배열이어야 합니다.")
        List<Long> favoriteRegions
) {

    public UserServiceUpdateRequest toService() {
        var userBasicInfoServiceDto = UserBasicInfoServiceRequest.builder()
            .nickname(basicInfo.nickname())
            .gender(basicInfo.gender())
            .birth(basicInfo.birth())
            .introduce(basicInfo.introduce())
            .build();

        var userFavoriteWorkingDayServiceDto = UserFavoriteWorkingDayServiceRequest.builder()
            .favoriteDate(favoriteWorkingDay.favoriteDate())
            .favoriteStartTime(favoriteWorkingDay.favoriteStartTime())
            .favoriteEndTime(favoriteWorkingDay.favoriteEndTime())
            .build();

        return UserServiceUpdateRequest.builder()
            .userBasicInfo(userBasicInfoServiceDto)
            .userFavoriteWorkingDay(userFavoriteWorkingDayServiceDto)
            .userFavoriteRegions(favoriteRegions)
            .build();
    }
}