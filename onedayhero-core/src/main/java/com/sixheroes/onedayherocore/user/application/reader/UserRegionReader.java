package com.sixheroes.onedayherocore.user.application.reader;

import com.sixheroes.onedayherocore.user.domain.User;
import com.sixheroes.onedayherocore.user.domain.UserRegion;
import com.sixheroes.onedayherocore.user.domain.repository.UserRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserRegionReader {

    private final UserRegionRepository userRegionRepository;

    public List<UserRegion> findAll(
        User user
    ) {
        return userRegionRepository.findAllByUser(user);
    }
}
