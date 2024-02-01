package com.sixheroes.onedayheroapi.chatroom.request;

import com.sixheroes.onedayherocore.missionchatroom.application.request.ChatMessageServiceRequest;
import com.sixheroes.onedayherocore.missionchatroom.mongo.ChatMessageType;
import lombok.Builder;

@Builder
public record ChatMessageRequest(
        Long chatRoomId,
        Long senderId,
        ChatMessageType messageType,
        String senderNickName,
        String message
) {

    public ChatMessageServiceRequest toService() {
        return ChatMessageServiceRequest.builder()
            .chatRoomId(chatRoomId)
            .senderId(senderId)
            .messageType(messageType)
            .senderNickName(senderNickName)
            .message(message)
            .build();
    }
}
