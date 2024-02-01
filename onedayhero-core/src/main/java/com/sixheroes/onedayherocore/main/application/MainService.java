package com.sixheroes.onedayherocore.main.application;

import com.sixheroes.onedayherocore.main.application.request.UserPositionServiceRequest;
import com.sixheroes.onedayherocore.main.application.response.MainResponse;
import com.sixheroes.onedayherocore.main.application.response.MissionSoonExpiredResponse;
import com.sixheroes.onedayherocore.mission.application.converter.PointConverter;
import com.sixheroes.onedayherocore.mission.application.response.MissionCategoryResponse;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionCategoryRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionImageRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MainService {

    private static final int DISTANCE = 3000;
    private final MissionCategoryRepository missionCategoryRepository;
    private final MissionRepository missionRepository;
    private final MissionImageRepository missionImageRepository;

    public MainResponse findMainResponse(
            Long userId,
            UserPositionServiceRequest request,
            LocalDateTime serverTime
    ) {
        var categoryResponses = missionCategoryRepository.findAll()
                .stream()
                .map(MissionCategoryResponse::from)
                .toList();

        var argsPoint = PointConverter.pointToString(request.longitude(), request.latitude());
        var limitTime = serverTime.plusHours(1);

        var soonExpiredMissionByLocation = missionRepository.findSoonExpiredMissionByLocation(argsPoint, DISTANCE, userId, serverTime, limitTime);

        var missionSoonExpiredResponses = soonExpiredMissionByLocation.stream()
                .map((response) -> {
                    var missionImages = missionImageRepository.findByMission_Id(response.getId());
                    var thumbNailPath = missionImages.isEmpty() ? null : missionImages.get(0).getPath();
                    return MissionSoonExpiredResponse.from(response, thumbNailPath);
                }).toList();

        return MainResponse.from(categoryResponses, missionSoonExpiredResponses);
    }
}
