package com.sixheroes.onedayherocore.missionchatroom.application.response;

import com.sixheroes.onedayherocore.missionchatroom.domain.MissionChatRoom;
import lombok.Builder;

@Builder
public record MissionChatRoomExitResponse(
        Long id,
        Long userId,
        Long missionId
) {

    public static MissionChatRoomExitResponse from(
            MissionChatRoom missionChatRoom,
            Long userId
    ) {
        return MissionChatRoomExitResponse.builder()
                .id(missionChatRoom.getId())
                .userId(userId)
                .missionId(missionChatRoom.getMissionId())
                .build();
    }
}
