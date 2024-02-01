package com.sixheroes.onedayherocore.user.application.repository.dto;

public record HeroRankQueryResponse(
    Long userId,
    String nickname,
    Integer heroScore,
    String profileImagePath
) {
}
