package com.sixheroes.onedayherocore.user.application.response;


import com.sixheroes.onedayherocore.region.domain.Region;

public record RegionForUserResponse(
    Long id,
    String dong
) {

    public static RegionForUserResponse from(
        Region region
    ) {
        return new RegionForUserResponse(region.getId(), region.getDong());
    }
}
