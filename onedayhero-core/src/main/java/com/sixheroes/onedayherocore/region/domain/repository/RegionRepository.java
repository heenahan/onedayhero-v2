package com.sixheroes.onedayherocore.region.domain.repository;

import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query("select r from UserRegion ur join Region r on ur.regionId = r.id where ur.user = :user")
    List<Region> findByUser(
            @Param("user") User user
    );

    Optional<Region> findByDong(String dong);
}