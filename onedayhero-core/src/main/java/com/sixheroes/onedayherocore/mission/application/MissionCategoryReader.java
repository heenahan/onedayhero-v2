package com.sixheroes.onedayherocore.mission.application;

import com.sixheroes.onedayherocommon.error.ErrorCode;
import com.sixheroes.onedayherocommon.exception.EntityNotFoundException;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionCategoryRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.dto.MissionCategoryDto;
import com.sixheroes.onedayherocore.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class MissionCategoryReader {

    private final MissionCategoryRepository missionCategoryRepository;

    public MissionCategory findOne(Long missionCategoryId) {
        return missionCategoryRepository.findById(missionCategoryId)
                .orElseThrow(() -> {
                    log.warn("존재하지 않는 미션 카테고리가 입력되었습니다. id : {}", missionCategoryId);
                    return new EntityNotFoundException(ErrorCode.NOT_FOUND_MISSION_CATEGORY);
                });
    }

    public Map<Long, List<MissionCategoryDto>> findAllByUser(
            List<User> user
    ) {
        var missionCategories = missionCategoryRepository.findMissionCategoriesByUser(user);

        return missionCategories.stream()
                .collect(Collectors.groupingBy(MissionCategoryDto::userId));
    }
}
