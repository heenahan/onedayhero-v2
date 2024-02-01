package com.sixheroes.onedayherocore.missionchatroom.application.response;

import com.sixheroes.onedayherocore.missionchatroom.domain.MissionChatRoom;
import lombok.Builder;

@Builder
public record MissionChatRoomCreateResponse(
        Long id,
        Long missionId,
        Integer headCount
) {

    public static MissionChatRoomCreateResponse from(
            MissionChatRoom missionChatRoom
    ) {
        return MissionChatRoomCreateResponse.builder()
                .id(missionChatRoom.getId())
                .missionId(missionChatRoom.getMissionId())
                .headCount(missionChatRoom.getHeadCount())
                .build();
    }
}
