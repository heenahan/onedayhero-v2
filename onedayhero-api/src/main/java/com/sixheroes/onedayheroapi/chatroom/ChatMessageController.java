package com.sixheroes.onedayheroapi.chatroom;

import com.sixheroes.onedayheroapi.chatroom.request.ChatMessageRequest;
import com.sixheroes.onedayherocore.missionchatroom.application.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatService chatService;

    @MessageMapping("/chatRooms/{chatRoomId}/chat")
    public void sendMessage(
            @DestinationVariable("chatRoomId") Long chatRoomId,
            ChatMessageRequest message
    ) {
        var serverTime = LocalDateTime.now();
        log.info("채팅 메시지가 도착했습니다. {}", message.message());
        chatService.send(chatRoomId, message.toService(), serverTime);
    }
}
