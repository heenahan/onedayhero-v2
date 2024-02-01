package com.sixheroes.onedayherocore.review.application.reqeust;

import com.sixheroes.onedayherocore.review.domain.Review;
import lombok.Builder;


@Builder
public record ReviewCreateServiceRequest(
        Long senderId,
        Long receiverId,
        Long missionId,
        Long categoryId,
        String missionTitle,
        String content,
        Integer starScore
) {

    public Review toEntity() {
        return Review.builder()
                .categoryId(categoryId)
                .missionTitle(missionTitle)
                .senderId(senderId)
                .receiverId(receiverId)
                .starScore(starScore)
                .content(content)
                .build();
    }
}
