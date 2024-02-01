package com.sixheroes.onedayherocore.review.application.response;

import org.springframework.data.domain.Slice;

public record ReceivedReviewViewResponse(
        Long userId,
        Slice<ReceivedReviewResponse> receivedReviewResponses
) {
}
