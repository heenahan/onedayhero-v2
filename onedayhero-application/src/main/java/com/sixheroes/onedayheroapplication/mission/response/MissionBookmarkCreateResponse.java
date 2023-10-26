package com.sixheroes.onedayheroapplication.mission.response;

import com.sixheroes.onedayherodomain.mission.MissionBookmark;

public record MissionBookmarkCreateResponse(
        Long id,
        Long missionId,
        Long userId
) {
    public MissionBookmarkCreateResponse(MissionBookmark missionBookmark) {
        this(
                missionBookmark.getId(),
                missionBookmark.getMission().getId(),
                missionBookmark.getUserId()
        );
    }
}
