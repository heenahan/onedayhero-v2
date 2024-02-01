package com.sixheroes.onedayherocore.missionchatroom.application.request;

import com.sixheroes.onedayherocore.missionchatroom.mongo.ChatMessage;
import com.sixheroes.onedayherocore.missionchatroom.mongo.ChatMessageType;
import com.sixheroes.onedayherocore.missionchatroom.mongo.request.MongoChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageServiceRequest(
    Long chatRoomId,
    Long senderId,
    ChatMessageType messageType,
    String senderNickName,
    String message
) {
    public MongoChatMessage toMongo() {
        return MongoChatMessage.builder()
            .chatRoomId(chatRoomId)
            .senderId(senderId)
            .senderNickName(senderNickName)
            .message(message)
            .build();
    }

    public static ChatMessageServiceRequest createLeaveMessage(
        ChatMessageServiceRequest request,
        String message
    ) {
        return ChatMessageServiceRequest.builder()
            .chatRoomId(request.chatRoomId)
            .senderId(request.senderId)
            .messageType(request.messageType)
            .senderNickName(request.senderNickName)
            .message(message)
            .build();
    }

    public ChatMessage toEntity(
        LocalDateTime serverTime
    ) {
        return ChatMessage.builder()
            .chatRoomId(chatRoomId)
            .senderId(senderId)
            .senderNickName(senderNickName)
            .message(message)
            .sentMessageTime(serverTime)
            .build();
    }
}
