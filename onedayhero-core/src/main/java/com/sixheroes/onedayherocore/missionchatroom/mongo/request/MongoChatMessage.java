package com.sixheroes.onedayherocore.missionchatroom.mongo.request;

import com.sixheroes.onedayherocore.missionchatroom.mongo.ChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MongoChatMessage(
        Long chatRoomId,
        Long senderId,
        String senderNickName,
        String message
) {

    public ChatMessage toEntity(
            String id,
            LocalDateTime serverTime
    ) {
        return ChatMessage.builder()
                .id(id)
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .senderNickName(senderNickName)
                .message(message)
                .sentMessageTime(serverTime)
                .build();
    }
}
