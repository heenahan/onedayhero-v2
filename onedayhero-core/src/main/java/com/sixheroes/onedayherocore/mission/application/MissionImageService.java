package com.sixheroes.onedayherocore.mission.application;

import com.sixheroes.onedayherocore.mission.domain.repository.MissionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MissionImageService {

    private final MissionImageReader missionImageReader;
    private final MissionImageRepository missionImageRepository;

    @Transactional
    public void deleteImage(
            Long missionImageId,
            Long userId
    ) {
        var missionImage = missionImageReader.findById(missionImageId);
        missionImage.validOwn(userId);

        missionImageRepository.delete(missionImage);
    }
}
