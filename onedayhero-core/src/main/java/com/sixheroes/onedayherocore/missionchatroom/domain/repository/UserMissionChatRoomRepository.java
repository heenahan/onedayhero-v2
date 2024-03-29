package com.sixheroes.onedayherocore.missionchatroom.domain.repository;

import com.sixheroes.onedayherocore.missionchatroom.domain.UserMissionChatRoom;
import com.sixheroes.onedayherocore.missionchatroom.domain.repository.response.UserChatRoomQueryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserMissionChatRoomRepository extends JpaRepository<UserMissionChatRoom, Long> {

    @Query("select um from UserMissionChatRoom um join fetch um.missionChatRoom where um.missionChatRoom.id = :chatRoomId and um.isJoined = true")
    List<UserMissionChatRoom> findByMissionChatRoom_Id(Long chatRoomId);

    List<UserMissionChatRoom> findByMissionChatRoom_IdIn(List<Long> missionChatRoomIds);

    @Query("""
            SELECT NEW com.sixheroes.onedayherocore.missionchatroom.domain.repository.response.UserChatRoomQueryResponse(
            mr.id, u.id, m.id, m.missionStatus, m.missionInfo.title, u.userBasicInfo.nickname, ui.path, mr.headCount
            )
            from UserMissionChatRoom um
            join MissionChatRoom mr on um.missionChatRoom.id = mr.id
            join Mission m on mr.missionId = m.id
            join User u on u.id = um.userId
            left join UserImage ui on um.userId = ui.user.id
            where um.missionChatRoom.id IN :chatRoomIds AND um.userId != :userId
            """)
    List<UserChatRoomQueryResponse> findReceiverChatRoomInfoInChatRoomIdsAndUserId(List<Long> chatRoomIds, Long userId);

    List<UserMissionChatRoom> findByUserIdAndIsJoinedTrue(Long userId);

    List<UserMissionChatRoom> findByUserIdInAndMissionChatRoom_MissionId(List<Long> userIds, Long missionId);
}