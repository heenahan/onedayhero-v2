package com.sixheroes.onedayherocore.review.application.response;

import com.sixheroes.onedayherocore.review.domain.ReviewImage;
import lombok.Builder;

@Builder
public record ReviewImageResponse(
        Long id,
        String originalName,
        String uniqueName,
        String path

) {

    public static ReviewImageResponse from(
            ReviewImage reviewImage
    ) {
        return ReviewImageResponse.builder()
                .id(reviewImage.getId())
                .originalName(reviewImage.getOriginalName())
                .uniqueName(reviewImage.getUniqueName())
                .path(reviewImage.getPath())
                .build();
    }
}
