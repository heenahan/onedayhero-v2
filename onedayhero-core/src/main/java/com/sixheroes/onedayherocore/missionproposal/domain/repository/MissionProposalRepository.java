package com.sixheroes.onedayherocore.missionproposal.domain.repository;

import com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalCreateEventDto;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalUpdateEventDto;
import com.sixheroes.onedayherocore.missionproposal.domain.MissionProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MissionProposalRepository extends JpaRepository<MissionProposal, Long> {

    @Query("""
        select 
         new com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalCreateEventDto(
            mp.heroId, u.userBasicInfo.nickname, m.id, m.missionInfo.title
         )
        from MissionProposal mp
        join Mission m on mp.missionId = m.id
        join User u on m.citizenId = u.id
        where mp.id = :missionProposalId
    """)
    Optional<MissionProposalCreateEventDto> findMissionProposalCreateEventDtoById(
        @Param("missionProposalId") Long missionProposalId
    );

    @Query("""
        select 
         new com.sixheroes.onedayherocore.missionproposal.domain.repository.dto.MissionProposalUpdateEventDto(
            m.citizenId, u.userBasicInfo.nickname, m.id, m.missionInfo.title
         )
        from MissionProposal mp
        join Mission m on mp.missionId = m.id
        join User u on mp.heroId = u.id
        where mp.id = :missionProposalId
    """)
    Optional<MissionProposalUpdateEventDto> findMissionProposalUpdateEventDtoById(
            @Param("missionProposalId") Long missionProposalId
    );

    List<MissionProposal> findByHeroId(Long herId);
}
