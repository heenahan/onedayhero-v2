package com.sixheroes.onedayherocore.review.application.response;

import java.time.LocalDateTime;

public record ReceivedReviewQueryResponse(
        Long reviewId,
        Long senderId,
        String senderNickname,
        String profileImage,
        String categoryName,
        String missionTitle,
        Integer starScore,
        LocalDateTime createdAt
) {
}
