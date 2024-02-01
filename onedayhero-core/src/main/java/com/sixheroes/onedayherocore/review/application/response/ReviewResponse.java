package com.sixheroes.onedayherocore.review.application.response;

import com.sixheroes.onedayherocore.review.domain.Review;
import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id
) {

    public static ReviewResponse from(
            Review review
    ) {
        return ReviewResponse.builder()
                .id(review.getId())
                .build();
    }
}
