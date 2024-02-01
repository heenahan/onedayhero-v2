package com.sixheroes.onedayherocore.review.application.response;

import java.time.LocalDateTime;

public record SentReviewQueryResponse(
        Long reviewId,
        String categoryName,
        String missionTitle,
        Integer starScore,
        String senderNickname,
        String profileImage,
        LocalDateTime createdAt
) {
}
