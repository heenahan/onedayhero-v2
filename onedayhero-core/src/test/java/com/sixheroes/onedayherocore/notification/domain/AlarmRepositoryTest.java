package com.sixheroes.onedayherocore.notification.domain;

import com.sixheroes.onedayherocore.IntegrationMongoRepositoryTest;
import com.sixheroes.onedayherocore.notification.mongo.Alarm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlarmRepositoryTest extends IntegrationMongoRepositoryTest {

    @AfterEach
    void teardown() {
        alarmRepository.deleteAll();
    }

    @DisplayName("알람 타입으로 알림 템플릿을 찾는다")
    @Test
    void findByAlarmType() {
        // given
        var alarms = createAlarms();
        alarmRepository.saveAll(alarms);

        var pageRequest = PageRequest.of(0, 3);

        // when
        var findAlarms = alarmRepository.findAllByUserId(1L, pageRequest);

        // then
        assertThat(findAlarms.getContent()).hasSize(3);
        assertThat(findAlarms.getContent()).isSortedAccordingTo(Comparator.comparing(Alarm::getCreatedAt).reversed());
    }

    private List<Alarm> createAlarms() {
        var alarm1 = Alarm.builder()
            .userId(1L)
            .title("알림 제목1")
            .content("알림 내용1")
            .build();

        var alarm2 = Alarm.builder()
            .userId(1L)
            .title("알림 제목2")
            .content("알림 내용2")
            .build();

        var alarm3 = Alarm.builder()
            .userId(1L)
            .title("알림 제목3")
            .content("알림 내용3")
            .build();

        return List.of(alarm1, alarm2, alarm3);
    }
}