package com.sixheroes.onedayherocore.missionchatroom.application;

import com.sixheroes.onedayherocore.missionchatroom.application.mapper.ChatMessageMapper;
import com.sixheroes.onedayherocore.missionchatroom.application.repository.MissionChatRoomRedisReader;
import com.sixheroes.onedayherocore.missionchatroom.application.repository.MissionChatRoomRedisRepository;
import com.sixheroes.onedayherocore.missionchatroom.application.request.ChatMessageServiceRequest;
import com.sixheroes.onedayherocore.missionchatroom.application.response.ChatMessageApiResponse;
import com.sixheroes.onedayherocore.missionchatroom.mongo.repository.ChatMessageRepository;
import com.sixheroes.onedayherocore.missionchatroom.infra.redis.handler.RedisChatPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final MissionChatRoomRedisReader missionChatRoomRedisReader;
    private final RedisChatPublisher publisher;
    private final ChatMessageRepository chatMessageRepository;
    private final MissionChatRoomRedisRepository redisRepository;

    public void send(
            Long chatRoomId,
            ChatMessageServiceRequest message,
            LocalDateTime serverTime
    ) {
        var topic = missionChatRoomRedisReader.findOne(chatRoomId);

        if (message.messageType().isLeave()) {
            message = ChatMessageMapper.toLeaveMessage(message);
        }

        publisher.publish(topic, message);

        var chatMessage = message.toEntity(serverTime);
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageApiResponse> findMessageByChatRoomId(
            Long chatRoomId
    ) {
        var chatRoomsMessages = chatMessageRepository.findAllByChatRoomId(chatRoomId);
        redisRepository.enterChatRoom(chatRoomId);

        return chatRoomsMessages.stream()
                .map(ChatMessageApiResponse::from)
                .toList();
    }
}
