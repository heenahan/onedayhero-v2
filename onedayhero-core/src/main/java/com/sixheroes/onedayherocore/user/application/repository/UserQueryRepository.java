package com.sixheroes.onedayherocore.user.application.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixheroes.onedayherocore.user.application.repository.dto.HeroRankQueryResponse;
import com.sixheroes.onedayherocore.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.sixheroes.onedayherocore.mission.domain.QMissionCategory.missionCategory;
import static com.sixheroes.onedayherocore.user.domain.QUser.user;
import static com.sixheroes.onedayherocore.user.domain.QUserImage.userImage;
import static com.sixheroes.onedayherocore.user.domain.QUserMissionCategory.userMissionCategory;
import static com.sixheroes.onedayherocore.user.domain.QUserRegion.userRegion;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<HeroRankQueryResponse> findHeroesRank(
        Long regionId,
        Long missionCategoryId,
        Pageable pageable
    ) {
        var pageSize = pageable.getPageSize();

        return queryFactory.select(
                Projections.constructor(HeroRankQueryResponse.class,
                    user.id,
                    user.userBasicInfo.nickname,
                    user.heroScore,
                    userImage.path
                )
            )
            .from(user)
            .join(userRegion)
            .on(userRegion.user.eq(user))
            .join(userMissionCategory)
            .on(userMissionCategory.user.eq(user))
            .leftJoin(userImage)
            .on(userImage.user.eq(user))
            .join(missionCategory)
            .on(missionCategory.id.eq(userMissionCategory.missionCategoryId))
            .where(createBooleanBuilder(regionId, missionCategoryId))
            .orderBy(user.heroScore.desc())
            .offset(pageable.getOffset())
            .limit(pageSize + 1L)
            .fetch();
    }

    private BooleanBuilder createBooleanBuilder(
        Long regionId,
        Long missionCategoryId
    ) {
        return regionIdEq(regionId).and(missionCategoryIdEq(missionCategoryId));
    }

    private BooleanBuilder regionIdEq(
        Long regionId
    ) {
        return new BooleanBuilder(userRegion.regionId.eq(regionId));
    }

    private BooleanBuilder missionCategoryIdEq(
        Long missionCategoryId
    ) {
        if (Objects.isNull(missionCategoryId)) return new BooleanBuilder();
        return new BooleanBuilder(userMissionCategory.missionCategoryId.eq(missionCategoryId));
    }
}
