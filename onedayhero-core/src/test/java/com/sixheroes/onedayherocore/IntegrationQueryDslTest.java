package com.sixheroes.onedayherocore;


import com.sixheroes.onedayherocore.mission.application.repository.MissionBookmarkQueryRepository;
import com.sixheroes.onedayherocore.mission.application.repository.MissionQueryRepository;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionBookmarkRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionCategoryRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionRepository;
import com.sixheroes.onedayherocore.missionproposal.application.repository.MissionProposalQueryRepository;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.MissionProposalRepository;
import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.region.domain.repository.RegionRepository;
import com.sixheroes.onedayherocore.review.application.repository.ReviewQueryRepository;
import com.sixheroes.onedayherocore.review.domain.repository.ReviewRepository;
import com.sixheroes.onedayherocore.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationQueryDslTest {

    @Autowired
    protected RegionRepository regionRepository;

    @Autowired
    protected MissionCategoryRepository missionCategoryRepository;

    @Autowired
    protected MissionRepository missionRepository;

    @Autowired
    protected MissionQueryRepository missionQueryRepository;

    @Autowired
    protected MissionProposalQueryRepository missionProposalQueryRepository;

    @Autowired
    protected MissionProposalRepository missionProposalRepository;

    @Autowired
    protected MissionBookmarkQueryRepository missionBookmarkQueryRepository;

    @Autowired
    protected MissionBookmarkRepository missionBookmarkRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ReviewQueryRepository reviewQueryRepository;

    @Autowired
    protected UserRepository userRepository;

    @BeforeAll
    public static void setUp(
            @Autowired MissionCategoryRepository missionCategoryRepository,
            @Autowired RegionRepository regionRepository
    ) {
        var missionCategories = Arrays.stream(MissionCategoryCode.values())
                .map(MissionCategory::createMissionCategory)
                .toList();

        missionCategoryRepository.saveAll(missionCategories);

        var regionA = Region.builder()
                .id(1L)
                .si("서울시")
                .gu("강남구")
                .dong("역삼동")
                .build();

        var regionB = Region.builder()
                .id(2L)
                .si("서울시")
                .gu("강남구")
                .dong("서초동")
                .build();

        var regionC = Region.builder()
                .id(3L)
                .si("서울시")
                .gu("강남구")
                .dong("역삼1동")
                .build();

        regionRepository.saveAll(List.of(regionA, regionB, regionC));
    }

    @AfterAll
    public static void tearDown(
            @Autowired MissionCategoryRepository missionCategoryRepository,
            @Autowired RegionRepository regionRepository
    ) {
        missionCategoryRepository.deleteAllInBatch();
        regionRepository.deleteAllInBatch();
    }
}
