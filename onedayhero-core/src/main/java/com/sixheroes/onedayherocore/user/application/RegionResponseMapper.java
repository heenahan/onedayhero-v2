package com.sixheroes.onedayherocore.user.application;

import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.user.application.response.RegionForUserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegionResponseMapper {

    public static Map<String, Map<String, List<RegionForUserResponse>>> toFavoriteRegions(
        List<Region> regions
    ) {
        return regions.stream()
            .collect(
                Collectors.groupingBy(
                    Region::getSi,
                    Collectors.groupingBy(
                        Region::getGu,
                        Collectors.mapping(RegionForUserResponse::from, Collectors.toList())
                    ))
            );
    }
}
