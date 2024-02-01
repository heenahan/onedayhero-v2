package com.sixheroes.onedayherocore.missionchatroom.application.mapper;


import com.sixheroes.onedayherocore.missionchatroom.application.request.ChatMessageServiceRequest;

public final class ChatMessageMapper {

    private final static String LEAVE_MESSAGE_FORMAT = "[알림] %s님이 나가셨습니다.";

    private ChatMessageMapper() {

    }

    public static ChatMessageServiceRequest toLeaveMessage(
            ChatMessageServiceRequest request
    ) {
        var leaveMessage = String.format(LEAVE_MESSAGE_FORMAT, request.senderNickName());
        return ChatMessageServiceRequest.createLeaveMessage(request, leaveMessage);
    }
}
