package com.sixheroes.onedayherocore.notification.application;

import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import com.sixheroes.onedayherocore.notification.application.dto.SsePayload;
import com.sixheroes.onedayherocore.notification.application.response.AlarmResponse;
import com.sixheroes.onedayherocore.notification.mongo.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final AlarmRepository alarmRepository;

    private final AlarmTemplateReader alarmTemplateReader;

    private final AlarmReader alarmReader;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void notifyClient(
        AlarmPayload alarmPayload
    ) {
        var alarmType = alarmPayload.alarmType();
        var alarmTemplate = alarmTemplateReader.findOne(alarmType);

        var alarm = alarmPayload.toEntity(alarmTemplate);
        alarmRepository.save(alarm);

        applicationEventPublisher.publishEvent(SsePayload.of(alarmType, alarm));
    }

    public Slice<AlarmResponse> findAlarm(
        Long userId,
        Pageable pageable
    ) {
        var alarms = alarmReader.findAll(userId, pageable);

        return alarms.map(AlarmResponse::from);
    }

    public void deleteAlarm(
        Long userId,
        String alarmId
    ) {
        var alarm = alarmReader.findOne(alarmId);

        alarm.validOwner(userId);

        alarmRepository.delete(alarm);
    }
}
