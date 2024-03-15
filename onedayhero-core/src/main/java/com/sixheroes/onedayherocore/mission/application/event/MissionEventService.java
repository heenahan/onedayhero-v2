package com.sixheroes.onedayherocore.mission.application.event;

import com.sixheroes.onedayherocore.event.domain.EventType;
import com.sixheroes.onedayherocore.event.domain.Events;
import com.sixheroes.onedayherocore.event.domain.repository.EventRepository;
import com.sixheroes.onedayherocore.mission.application.MissionReader;
import com.sixheroes.onedayherocore.mission.application.converter.MissionEventMapper;
import com.sixheroes.onedayherocore.mission.application.event.dto.MissionCompletedEvent;
import com.sixheroes.onedayherocore.notification.application.NotificationService;
import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.config.TransactionManagementConfigUtils;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
public class MissionEventService {

    private final MissionReader missionReader;
    private final NotificationService notificationService;
    private final EventRepository eventRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public CompletableFuture<Void> notifyMissionCompleted(
        MissionCompletedEvent missionCompletedEvent
    ) {
         return CompletableFuture.supplyAsync(() -> {
            var missionId = missionCompletedEvent.missionId();
            var missionCompletedEventQueryResponse = missionReader.findMissionCompletedEvent(missionId);

            return MissionEventMapper.toAlarmPayload(missionCompletedEventQueryResponse); })
         .thenApply(alarmPayload -> {
             var events = Events.builder()
                 .eventType(EventType.ALARM)
                 .eventData(alarmPayload)
                 .build();

             return eventRepository.save(events); })
         .thenApply(events -> {
                 var alarmPayload = (AlarmPayload) events.getEventData();
                 notificationService.notifyClient(alarmPayload);

                 return events; })
         .thenAccept(events -> {
             events.changeSuccess();
             eventRepository.save(events); });
    }
}
