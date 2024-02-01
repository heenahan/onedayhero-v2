package com.sixheroes.onedayherocore.missionchatroom.mongo.repository;

import com.sixheroes.onedayherocore.missionchatroom.mongo.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findAllByChatRoomId(Long chatRoomId);
}
