package com.sixheroes.onedayherocore.user.application;

import com.sixheroes.onedayherocore.global.util.SliceResultConverter;
import com.sixheroes.onedayherocore.mission.application.MissionCategoryReader;
import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;
import com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionCategoryDto;
import com.sixheroes.onedayherocore.region.application.RegionReader;
import com.sixheroes.onedayherocore.user.application.reader.UserReader;
import com.sixheroes.onedayherocore.user.application.repository.dto.HeroRankQueryResponse;
import com.sixheroes.onedayherocore.user.application.request.HeroRankServiceRequest;
import com.sixheroes.onedayherocore.user.application.response.HeroRankResponse;
import com.sixheroes.onedayherocore.user.application.response.HeroSearchResponse;
import com.sixheroes.onedayherocore.user.application.response.ProfileCitizenResponse;
import com.sixheroes.onedayherocore.user.application.response.ProfileHeroResponse;
import com.sixheroes.onedayherocore.user.domain.repository.UserMissionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProfileService {

    private final UserReader userReader;
    private final RegionReader regionReader;
    private final MissionCategoryReader missionCategoryReader;

    private final UserMissionCategoryRepository userMissionCategoryRepository;

    public ProfileCitizenResponse findCitizenProfile(
        Long userId
    ) {
        var user = userReader.findOne(userId);

        return ProfileCitizenResponse.from(user);
    }

    public ProfileHeroResponse findHeroProfile(
        Long userId
    ) {
        var user = userReader.findOne(userId);
        user.validPossibleHeroProfile();

        var regions = regionReader.findByUser(user);

        return ProfileHeroResponse.from(user, regions);
    }

    public Slice<HeroSearchResponse> searchHeroes(
        String nickname,
        Pageable pageable
    ) {
        var heroes = userReader.findHeroes(nickname, pageable);

        var missionCategories = missionCategoryReader.findAllByUser(heroes.getContent());

        return heroes.map(u -> HeroSearchResponse.of(u, missionCategories.get(u.getId())));
    }

    @Cacheable("heroesRank")
    public Slice<HeroRankResponse> findHeroesRank(
        HeroRankServiceRequest request,
        Pageable pageable
    ) {
        var categoryId = Optional.ofNullable(request.missionCategoryCode())
            .map(MissionCategoryCode::getCategoryId)
            .orElse(null);
        var region = regionReader.findByDong(request.regionName());

        var heroesRank = userReader.findHeroesRank(region.getId(), categoryId, pageable);

        var heroRankResponses = addRank(heroesRank, pageable);
        return SliceResultConverter.consume(heroRankResponses, pageable);
    }

    private List<HeroRankResponse> addRank(
        List<HeroRankQueryResponse> heroRankQueryResponses,
        Pageable pageable
    ) {
        var userIds = heroRankQueryResponses.stream()
            .map(HeroRankQueryResponse::userId)
            .toList();
        var missionCategories = userMissionCategoryRepository.findByUsers(userIds);
        var missionCategoriesMapping = missionCategories.stream()
            .collect(Collectors.groupingBy(MissionCategoryDto::userId));

        return IntStream.range(0, heroRankQueryResponses.size()).mapToObj(i -> {
            var heroRankQueryResponse = heroRankQueryResponses.get(i);
            var favoriteMissionCategories = missionCategoriesMapping.get(heroRankQueryResponse.userId());
            return HeroRankResponse.of(heroRankQueryResponse, favoriteMissionCategories, pageable, i);
        }).toList();
    }
}
