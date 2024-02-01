package com.sixheroes.onedayherocore.mission.domain.repository;

import com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionCategoryDto;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import com.sixheroes.onedayherocore.user.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Qualifier("missionCategoryRepository")
public interface MissionCategoryRepository extends JpaRepository<MissionCategory, Long> {

    @Query("""
        select new com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionCategoryDto(
            umc.user.id, mc.missionCategoryCode, mc.name
        )
        from MissionCategory mc
        join UserMissionCategory umc on mc.id = umc.missionCategoryId
        where umc.user in :users
    """)
    List<MissionCategoryDto> findMissionCategoriesByUser(@Param("users") List<User> users);
}
