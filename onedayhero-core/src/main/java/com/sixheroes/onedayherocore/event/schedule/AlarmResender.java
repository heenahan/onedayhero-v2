package com.sixheroes.onedayherocore.event.schedule;

import com.sixheroes.onedayherocore.event.domain.EventService;
import com.sixheroes.onedayherocore.event.domain.Events;
import com.sixheroes.onedayherocore.event.domain.repository.EventRepository;
import com.sixheroes.onedayherocore.notification.application.NotificationService;
import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmResender {

    private static final int FIVE_MINUTE = 5;

    private final EventRepository eventRepository;
    private final EventService<AlarmPayload> eventService;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void resendAlarm() {
        var fiveMinutesAgo = LocalDateTime.now().minusMinutes(FIVE_MINUTE);
        var events = eventRepository.findAllBySuccessIsFalse(fiveMinutesAgo);
        for (Events event : events) {
            CompletableFuture.runAsync(() -> {
                var alarmPayload = eventService.convertStringToObject(event);
                notificationService.notifyClient(alarmPayload);
            }).thenRun(() -> event.changeSuccess());
        }
    }
}
