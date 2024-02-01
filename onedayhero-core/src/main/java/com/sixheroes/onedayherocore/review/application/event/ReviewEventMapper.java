package com.sixheroes.onedayherocore.review.application.event;


import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import com.sixheroes.onedayherocore.review.application.event.dto.ReviewEventAction;
import com.sixheroes.onedayherocore.review.domain.repository.dto.ReviewCreateEventDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReviewEventMapper {

    public static AlarmPayload toAlarmPayload(
        ReviewCreateEventDto reviewCreateEventDto
    ) {
        return AlarmPayload.builder()
            .alarmType(ReviewEventAction.REVIEW_CREATE.name())
            .userId(reviewCreateEventDto.receiverId())
            .data(reviewCreateEventDto.toMap())
            .build();
    }
}
