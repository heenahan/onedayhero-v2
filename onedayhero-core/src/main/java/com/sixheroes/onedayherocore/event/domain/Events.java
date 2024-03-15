package com.sixheroes.onedayherocore.event.domain;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.BusinessException;
import com.sixheroes.onedayherocore.global.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.Event;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "events")
@Entity
public class Events extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Convert(converter = EventDataConverter.class)
    @Column(name = "data", length = 255, nullable = false)
    private Object eventData;

    @Column(name = "success", nullable = false)
    private Boolean success = Boolean.FALSE;

    @Builder
    private Events(
        EventType eventType,
        Object eventData
    ) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public void changeSuccess() {
        if (success.equals(Boolean.TRUE)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST_VALUE);
        }
        this.success = Boolean.TRUE;
    }
}
