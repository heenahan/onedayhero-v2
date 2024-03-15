package com.sixheroes.onedayherocore;

import com.sixheroes.onedayherocore.mission.application.MissionService;
import com.sixheroes.onedayherocore.mission.application.event.MissionEventService;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionCategoryRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionRepository;
import com.sixheroes.onedayherocore.missionmatch.application.MissionMatchService;
import com.sixheroes.onedayherocore.missionmatch.application.event.MissionMatchEventService;
import com.sixheroes.onedayherocore.missionmatch.domain.repository.MissionMatchRepository;
import com.sixheroes.onedayherocore.missionproposal.application.MissionProposalService;
import com.sixheroes.onedayherocore.missionproposal.application.event.MissionProposalEventService;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.MissionProposalRepository;
import com.sixheroes.onedayherocore.notification.application.NotificationService;
import com.sixheroes.onedayherocore.notification.mongo.repository.AlarmRepository;
import com.sixheroes.onedayherocore.notification.mongo.repository.AlarmTemplateRepository;
import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.region.domain.repository.RegionRepository;
import com.sixheroes.onedayherocore.review.application.ReviewService;
import com.sixheroes.onedayherocore.review.application.event.ReviewEventService;
import com.sixheroes.onedayherocore.review.domain.repository.ReviewRepository;
import com.sixheroes.onedayherocore.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@RecordApplicationEvents
public abstract class IntegrationApplicationEventTest {

    @Autowired
    protected ApplicationEvents applicationEvents;

    @MockBean
    protected AlarmTemplateRepository alarmTemplateRepository;

    @MockBean
    protected AlarmRepository alarmRepository;

    @Autowired
    protected NotificationService notificationService;

    @Autowired
    protected MissionProposalService missionProposalService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MissionProposalRepository missionProposalRepository;

    @Autowired
    protected MissionCategoryRepository missionCategoryRepository;

    @Autowired
    protected MissionRepository missionRepository;

    @Autowired
    protected MissionProposalEventService missionProposalEventService;

    @Autowired
    protected ReviewService reviewService;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ReviewEventService reviewEventService;

    @Autowired
    protected MissionService missionService;

    @Autowired
    protected MissionEventService missionEventService;

    @Autowired
    protected MissionMatchRepository missionMatchRepository;

    @Autowired
    protected MissionMatchService missionMatchService;

    @Autowired
    protected MissionMatchEventService missionMatchEventService;

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
