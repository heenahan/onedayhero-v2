package com.sixheroes.onedayherocore.missionchatroom.mongo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ChatMessageType {
    TALK("대화"),
    LEAVE("나가기");

    private final String description;

    public boolean isLeave() {
        return this == LEAVE;
    }
}
