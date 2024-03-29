package com.sixheroes.onedayheroapi.chatroom;

import com.sixheroes.onedayheroapi.chatroom.request.CreateMissionChatRoomRequest;
import com.sixheroes.onedayheroapi.global.argumentsresolver.authuser.AuthUser;
import com.sixheroes.onedayheroapi.global.response.ApiResponse;
import com.sixheroes.onedayherocore.missionchatroom.application.ChatRoomService;
import com.sixheroes.onedayherocore.missionchatroom.application.ChatService;
import com.sixheroes.onedayherocore.missionchatroom.application.response.ChatMessageApiResponse;
import com.sixheroes.onedayherocore.missionchatroom.application.response.MissionChatRoomCreateResponse;
import com.sixheroes.onedayherocore.missionchatroom.application.response.MissionChatRoomExitResponse;
import com.sixheroes.onedayherocore.missionchatroom.application.response.MissionChatRoomFindResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat-rooms")
public class ChatRoomController {

    private static final String CHAT_ROOM_URI_FORMAT = "/api/v1/chat-rooms/";
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ApiResponse<MissionChatRoomCreateResponse>> createChatRoom(
            @RequestBody @Valid CreateMissionChatRoomRequest request
    ) {
        var result = chatRoomService.createChatRoom(request.toService());
        return ResponseEntity.created(URI.create(CHAT_ROOM_URI_FORMAT + result.id()))
                .body(ApiResponse.created(result));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<MissionChatRoomFindResponse>>> findByChatRoomId(
            @AuthUser Long userId
    ) {
        var result = chatRoomService.findJoinedChatRoom(userId);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponse<List<ChatMessageApiResponse>>> findChatMessagesByChatRoomId(
            @PathVariable Long chatRoomId
    ) {
        var result = chatService.findMessageByChatRoomId(chatRoomId);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PatchMapping("/{chatRoomId}/exit")
    public ResponseEntity<ApiResponse<MissionChatRoomExitResponse>> exitChatRoom(
            @PathVariable Long chatRoomId,
            @AuthUser Long userId
    ) {
        var missionChatRoomResponse = chatRoomService.exitChatRoom(chatRoomId, userId);

        return ResponseEntity.ok(ApiResponse.ok(missionChatRoomResponse));
    }
}
