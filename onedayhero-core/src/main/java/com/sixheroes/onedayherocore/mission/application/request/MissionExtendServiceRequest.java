package com.sixheroes.onedayherocore.mission.application.request;

import com.sixheroes.onedayherocore.mission.domain.MissionInfo;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record MissionExtendServiceRequest(
        Long userId,
        LocalDate missionDate,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime deadlineTime
) {

    public MissionInfo toVo(
            MissionInfo missionInfo,
            LocalDateTime serverTime
    ) {
        return missionInfo.extend(missionDate, startTime, endTime, deadlineTime, serverTime);
    }
}
