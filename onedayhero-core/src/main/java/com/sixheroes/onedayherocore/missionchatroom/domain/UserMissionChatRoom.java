package com.sixheroes.onedayherocore.missionchatroom.domain;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.BusinessException;
import com.sixheroes.onedayherocore.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_mission_chat_rooms",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"mission_chat_room_id", "user_id"})
        })
@Entity
public class UserMissionChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_chat_room_id", nullable = false)
    private MissionChatRoom missionChatRoom;

    @Column(name = "is_joined", nullable = false)
    private Boolean isJoined;

    @Builder
    private UserMissionChatRoom(
            Long userId,
            MissionChatRoom missionChatRoom
    ) {
        this.userId = userId;
        this.missionChatRoom = missionChatRoom;
        this.isJoined = true;
    }

    public static UserMissionChatRoom createUserMissionChatRoom(
            Long userId,
            MissionChatRoom missionChatRoom
    ) {
        return UserMissionChatRoom.builder()
                .userId(userId)
                .missionChatRoom(missionChatRoom)
                .build();
    }

    public boolean isUserChatRoom(Long userId) {
        return this.userId.equals(userId);
    }

    public void exit() {
        if (!isJoined) {
            log.warn("채팅방에 접속해있는 상태에서만 퇴장이 가능합니다. {}", isJoined);
            throw new BusinessException(ErrorCode.ABORT_CHATROOM_EXIT);
        }

        missionChatRoom.minusHeadCount();
        isJoined = false;
    }
}