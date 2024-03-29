package com.sixheroes.onedayherocore.mission.application.request;

import com.sixheroes.onedayherocore.mission.domain.MissionInfo;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record MissionInfoServiceRequest(
        String title,
        String content,
        LocalDate missionDate,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime deadlineTime,
        Integer price
) {

    public MissionInfo toVo(LocalDateTime serverTime) {
        return MissionInfo.builder()
                .title(title)
                .content(content)
                .missionDate(missionDate)
                .startTime(startTime)
                .endTime(endTime)
                .deadlineTime(deadlineTime)
                .price(price)
                .serverTime(serverTime)
                .build();
    }
}
