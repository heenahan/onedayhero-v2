package com.sixheroes.onedayherocore.mission.domain.repository;

import com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionImageQueryResponse;
import com.sixheroes.onedayherocore.mission.domain.MissionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionImageRepository extends JpaRepository<MissionImage, Long> {

    List<MissionImage> findByMission_Id(Long missionId);

    List<MissionImage> findByIdIn(List<Long> ids);

    @Query("""  
                select 
                    new com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionImageQueryResponse(
                        m.id, mi.path
                    )
                from MissionImage mi
                join Mission m on mi.mission.id = m.id
                where mi.mission.id in :missionIds
            """)
    List<MissionImageQueryResponse> findByMissionIdIn(@Param("missionIds") List<Long> missionIds);
}
