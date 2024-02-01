package com.sixheroes.onedayherocore.review.application.event.dto;

import com.sixheroes.onedayherocore.review.domain.Review;

public record ReviewCreateEvent(
    Long reviewId
) {

    public static ReviewCreateEvent from(
        Review review
    ) {
        return new ReviewCreateEvent(review.getId());
    }
}
