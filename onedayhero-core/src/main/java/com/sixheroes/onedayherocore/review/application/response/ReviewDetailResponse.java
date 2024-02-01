package com.sixheroes.onedayherocore.review.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixheroes.onedayherocore.review.application.repository.response.ReviewDetailQueryResponse;
import com.sixheroes.onedayherocore.review.domain.ReviewImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.sixheroes.onedayherocore.review.application.response.ReceivedReviewResponse.imageMapper;

@Builder
public record ReviewDetailResponse(
        Long id,

        Long senderId,

        String senderNickname,

        List<String> senderProfileImage,

        Long receiverId,

        String receiverNickname,

        Long categoryId,

        String categoryCode,

        String categoryName,

        String missionTitle,

        String content,

        Integer starScore,

        List<ReviewImageResponse> reviewImageResponses,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {
        private static Function<Optional<List<ReviewImage>>, List<ReviewImageResponse>> responseMapper = reviewImages ->
             reviewImages.map(images ->
                             images
                                     .stream()
                                     .map(ReviewImageResponse::from)
                                     .toList()
                     ).orElse(Collections.emptyList()
             );

        public static ReviewDetailResponse of(
                ReviewDetailQueryResponse queryResponse,
                Optional<List<ReviewImage>> reviewImages,
                String receiverNickname
        ) {
                return ReviewDetailResponse.builder()
                        .id(queryResponse.id())
                        .senderId(queryResponse.senderId())
                        .senderNickname(queryResponse.senderNickname())
                        .senderProfileImage(imageMapper(queryResponse.senderProfileImage()))
                        .receiverId(queryResponse.receiverId())
                        .receiverNickname(receiverNickname)
                        .categoryId(queryResponse.categoryId())
                        .categoryCode(queryResponse.categoryCode().name())
                        .categoryName(queryResponse.categoryName())
                        .missionTitle(queryResponse.missionTitle())
                        .content(queryResponse.content())
                        .starScore(queryResponse.starScore())
                        .createdAt(queryResponse.createdAt())
                        .reviewImageResponses(responseMapper.apply(reviewImages))
                        .build();
        }

}
