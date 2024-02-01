package com.sixheroes.onedayherocore.mission.domain.repository;

import com.sixheroes.onedayherocore.mission.domain.MissionBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionBookmarkRepository extends JpaRepository<MissionBookmark, Long> {

    Optional<MissionBookmark> findByMissionIdAndUserId(Long missionId, Long userId);

    List<MissionBookmark> findByMissionId(Long missionId);

    void deleteByIdIn(List<Long> userBookmarkIds);
}
