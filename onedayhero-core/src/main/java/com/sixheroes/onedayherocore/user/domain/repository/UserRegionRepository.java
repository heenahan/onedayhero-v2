package com.sixheroes.onedayherocore.user.domain.repository;

import com.sixheroes.onedayherocore.user.domain.User;
import com.sixheroes.onedayherocore.user.domain.UserRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRegionRepository extends JpaRepository<UserRegion, Long> {

    List<UserRegion> findAllByUser(User user);
}
