package com.sixheroes.onedayherocore.review.application.repository.response;

import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;

import java.time.LocalDateTime;

public record ReviewDetailQueryResponse(
        Long id,

        Long senderId,

        String senderNickname,

        String senderProfileImage,

        Long receiverId,

        Long categoryId,

        MissionCategoryCode categoryCode,

        String categoryName,

        String missionTitle,

        String content,

        Integer starScore,

        LocalDateTime createdAt
) {
}
