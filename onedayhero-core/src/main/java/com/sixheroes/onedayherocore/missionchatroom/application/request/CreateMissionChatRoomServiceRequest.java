package com.sixheroes.onedayherocore.missionchatroom.application.request;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateMissionChatRoomServiceRequest(
        Long missionId,
        List<Long> userIds
) {
}
