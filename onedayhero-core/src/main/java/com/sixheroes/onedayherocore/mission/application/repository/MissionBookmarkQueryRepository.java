package com.sixheroes.onedayherocore.mission.application.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixheroes.onedayherocore.global.util.SliceResultConverter;
import com.sixheroes.onedayherocore.mission.application.repository.response.MissionBookmarkMeQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import static com.sixheroes.onedayherocore.mission.domain.QMission.mission;
import static com.sixheroes.onedayherocore.mission.domain.QMissionBookmark.missionBookmark;
import static com.sixheroes.onedayherocore.mission.domain.QMissionCategory.missionCategory;
import static com.sixheroes.onedayherocore.region.domain.QRegion.region;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MissionBookmarkQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<MissionBookmarkMeQueryResponse> viewMyBookmarks(
            Pageable pageable,
            Long userId
    ) {
        var content = queryFactory
                .select(
                        Projections.constructor(
                                MissionBookmarkMeQueryResponse.class,
                                mission.id,
                                missionBookmark.id,
                                mission.missionStatus,
                                mission.missionInfo.title,
                                mission.bookmarkCount,
                                mission.missionInfo.price,
                                mission.missionInfo.missionDate,
                                mission.missionInfo.startTime,
                                mission.missionInfo.endTime,
                                mission.missionCategory.name, //TODO: categoryId 캐싱 고려
                                region.id, //TODO: regionId 캐싱 고려
                                region.si,
                                region.gu,
                                region.dong
                        )
                )
                .from(mission)
                .join(mission.missionCategory, missionCategory)
                .join(missionBookmark).on(mission.id.eq(missionBookmark.mission.id))
                .join(region).on(mission.regionId.eq(region.id))
                .where(missionBookmark.userId.eq(userId))
                .orderBy(mission.missionInfo.missionDate.asc(), mission.missionInfo.startTime.asc())
                .offset(pageable.getOffset()) //TODO: 인덱스 및 NO-OFFSET 적용 고려
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        return SliceResultConverter.consume(
                content,
                pageable
        );
    }
}
