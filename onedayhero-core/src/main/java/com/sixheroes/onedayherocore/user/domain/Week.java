package com.sixheroes.onedayherocore.user.domain;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Week {
    MON("월요일"),
    TUE("화요일"),
    WED("수요일"),
    THU("목요일"),
    FRI("금요일"),
    SAT("토요일"),
    SUN("일요일");

    private final String description;

    public static Week from(String week) {
        return Arrays.stream(Week.values())
                .filter(w -> w.isEqual(week))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.INVALID_WEEK));
    }

    private boolean isEqual(String week) {
        return week.equals(this.name());
    }
}