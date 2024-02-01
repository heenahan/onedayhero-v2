package com.sixheroes.onedayherocore.missionchatroom.application;

import com.sixheroes.onedayherocore.IntegrationRedisTest;
import com.sixheroes.onedayherocore.missionchatroom.application.repository.MissionChatRoomRedisRepository;
import com.sixheroes.onedayherocore.missionchatroom.application.repository.request.MissionChatRoomRedisRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
class MissionChatRoomRedisRepositoryTest extends IntegrationRedisTest {

    @Autowired
    private MissionChatRoomRedisRepository missionChatRoomRedisRepository;

    @DisplayName("유저가 채팅방을 만들면 캐시에 저장이 된다.")
    @Test
    void createChatRoom() {
        // given
        var missionId = 1L;

        var missionChatRoom = MissionChatRoomRedisRequest.builder()
                .id(1L)
                .missionId(missionId)
                .build();

        // when
        missionChatRoomRedisRepository.create(missionChatRoom);

        var missionChatRooms = missionChatRoomRedisRepository.findAll();

        // then
        assertThat(missionChatRooms).hasSize(1);
    }
}