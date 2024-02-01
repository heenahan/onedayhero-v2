package com.sixheroes.onedayherocore.user.application.response;

import com.sixheroes.onedayherocore.user.domain.User;

public record UserUpdateResponse(
    Long id
) {

    public static UserUpdateResponse from(
        User user
    ) {
        return new UserUpdateResponse(user.getId());
    }
}
