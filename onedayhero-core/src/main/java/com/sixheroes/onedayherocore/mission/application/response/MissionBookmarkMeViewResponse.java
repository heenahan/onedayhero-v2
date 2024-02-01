package com.sixheroes.onedayherocore.mission.application.response;

import com.sixheroes.onedayherocore.mission.application.repository.response.MissionBookmarkMeQueryResponse;
import org.springframework.data.domain.Slice;

public record MissionBookmarkMeViewResponse(
        Long userId,
        Slice<MissionBookmarkMeResponse> missionBookmarkMeResponses
) {

    public static MissionBookmarkMeViewResponse of(
            Long userId,
            Slice<MissionBookmarkMeQueryResponse> responses
    ) {
        var missionBookmarkMeResponses = responses.map(MissionBookmarkMeResponse::from);
        return new MissionBookmarkMeViewResponse(userId, missionBookmarkMeResponses);
    }
}
