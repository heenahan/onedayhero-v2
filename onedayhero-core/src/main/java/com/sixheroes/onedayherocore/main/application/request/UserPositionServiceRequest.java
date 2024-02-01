package com.sixheroes.onedayherocore.main.application.request;

import lombok.Builder;

@Builder
public record UserPositionServiceRequest(
        double longitude,
        double latitude
) {

}
