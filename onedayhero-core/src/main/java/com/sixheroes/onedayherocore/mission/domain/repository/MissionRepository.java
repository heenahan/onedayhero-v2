package com.sixheroes.onedayherocore.mission.domain.repository;

import com.sixheroes.onedayherocore.mission.domain.repository.response.MainMissionQueryResponse;
import com.sixheroes.onedayherocore.mission.domain.repository.response.MissionAroundQueryResponse;
import com.sixheroes.onedayherocore.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(
            value = """
                    SELECT
                          m.id,
                          mc.id as missionCategoryId,
                          mc.code as missionCategoryCode,
                          mc.name as missionCategoryName,
                          r.id as regionId,
                          r.si,
                          r.gu,
                          r.dong,
                          m.title,
                          ST_AsText(m.location) as location,
                          m.mission_date as missionDate,
                          m.start_time as startTime,
                          m.end_time as endTime,
                          m.price as price
                      FROM missions m
                      JOIN m_categories mc ON m.category_id = mc.id
                      JOIN regions r ON r.id = m.region_id
                      WHERE ST_Contains(ST_Buffer(ST_GeomFromText(:pre_location, 4326), :distance), m.location)
                      AND m.status = 'MATCHING'
                      ORDER BY m.created_at ASC
                      LIMIT :size OFFSET :page
                    """, nativeQuery = true
    )
    List<MissionAroundQueryResponse> findAroundMissionByLocation(
            @Param("pre_location") String preLocation,
            @Param("distance") Integer distance,
            @Param("size") Integer size,
            @Param("page") Long page
    );

    @Query(
            value = """
                    SELECT
                          m.id,
                          mc.id as categoryId,
                          mc.code as categoryCode,
                          mc.name as categoryName,
                          m.citizen_id as citizenId,
                          r.id as regionId,
                          r.si,
                          r.gu,
                          r.dong,
                          ST_AsText(m.location) as location,
                          m.title,
                          m.mission_date as missionDate,
                          m.start_time as startTime,
                          m.end_time as endTime,
                          m.deadline_time as deadlineTime,
                          m.price as price,
                          m.bookmark_count as bookmarkCount,
                          m.status as missionStatus,
                          mb.id AS missionBookmarkId
                      FROM missions m
                      JOIN m_categories mc ON m.category_id = mc.id
                      JOIN regions r ON r.id = m.region_id
                      LEFT JOIN mission_bookmarks mb ON mb.mission_id = m.id and mb.user_id = :user_id
                      WHERE ST_Contains(ST_Buffer(ST_GeomFromText(:pre_location, 4326), :distance), m.location)
                      AND m.status = 'MATCHING'
                      AND m.deadline_time BETWEEN :server_time AND :limit_time
                      ORDER BY m.deadline_time ASC
                      limit 10;
                    """, nativeQuery = true
    )
    List<MainMissionQueryResponse> findSoonExpiredMissionByLocation(
            @Param("pre_location") String preLocation,
            @Param("distance") Integer distance,
            @Param("user_id") Long userId,
            @Param("server_time") LocalDateTime serverTime,
            @Param("limit_time") LocalDateTime limitTime);
}
