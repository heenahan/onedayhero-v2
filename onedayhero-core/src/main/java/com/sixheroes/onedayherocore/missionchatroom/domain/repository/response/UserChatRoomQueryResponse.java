package com.sixheroes.onedayherocore.missionchatroom.domain.repository.response;

import com.sixheroes.onedayherocore.mission.domain.MissionStatus;

public record UserChatRoomQueryResponse(
        Long chatRoomId,
        Long receiverId,
        Long missionId,
        MissionStatus missionStatus,
        String title,
        String nickName,
        String path,
        Integer headCount
) {
}
