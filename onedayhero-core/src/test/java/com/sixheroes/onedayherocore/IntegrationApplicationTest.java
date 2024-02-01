package com.sixheroes.onedayherocore;

import com.sixheroes.onedayherocore.global.s3.S3ImageDeleteService;
import com.sixheroes.onedayherocore.global.s3.S3ImageUploadService;
import com.sixheroes.onedayherocore.main.application.MainService;
import com.sixheroes.onedayherocore.mission.application.MissionBookmarkService;
import com.sixheroes.onedayherocore.mission.application.MissionImageService;
import com.sixheroes.onedayherocore.mission.application.MissionService;
import com.sixheroes.onedayherocore.mission.domain.MissionCategory;
import com.sixheroes.onedayherocore.mission.domain.MissionCategoryCode;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionBookmarkRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionCategoryRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionImageRepository;
import com.sixheroes.onedayherocore.mission.domain.repository.MissionRepository;
import com.sixheroes.onedayherocore.missionchatroom.application.ChatRoomService;
import com.sixheroes.onedayherocore.missionchatroom.application.repository.MissionChatRoomRedisRepository;
import com.sixheroes.onedayherocore.missionchatroom.mongo.repository.ChatMessageMongoRepository;
import com.sixheroes.onedayherocore.missionchatroom.domain.repository.MissionChatRoomRepository;
import com.sixheroes.onedayherocore.missionchatroom.domain.repository.UserMissionChatRoomRepository;
import com.sixheroes.onedayherocore.missionmatch.application.MissionMatchReader;
import com.sixheroes.onedayherocore.missionmatch.application.MissionMatchService;
import com.sixheroes.onedayherocore.missionproposal.application.MissionProposalService;
import com.sixheroes.onedayherocore.missionproposal.domain.repository.MissionProposalRepository;
import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.region.domain.repository.RegionRepository;
import com.sixheroes.onedayherocore.review.application.ReviewImageService;
import com.sixheroes.onedayherocore.review.application.ReviewService;
import com.sixheroes.onedayherocore.review.domain.repository.ReviewImageRepository;
import com.sixheroes.onedayherocore.review.domain.repository.ReviewRepository;
import com.sixheroes.onedayherocore.user.application.ProfileService;
import com.sixheroes.onedayherocore.user.application.UserService;
import com.sixheroes.onedayherocore.user.application.repository.UserQueryRepository;
import com.sixheroes.onedayherocore.user.application.validation.UserValidation;
import com.sixheroes.onedayherocore.user.domain.repository.UserImageRepository;
import com.sixheroes.onedayherocore.user.domain.repository.UserMissionCategoryRepository;
import com.sixheroes.onedayherocore.user.domain.repository.UserRegionRepository;
import com.sixheroes.onedayherocore.user.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
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

public abstract class IntegrationApplicationTest {

    @Autowired
    protected MissionImageRepository missionImageRepository;

    @Autowired
    protected MissionBookmarkRepository missionBookmarkRepository;

    @Autowired
    protected MissionCategoryRepository missionCategoryRepository;

    @Autowired
    protected MissionRepository missionRepository;

    @Autowired
    protected RegionRepository regionRepository;

    @Autowired
    protected MissionChatRoomRepository missionChatRoomRepository;

    @Autowired
    protected UserMissionChatRoomRepository userMissionChatRoomRepository;

    @Autowired
    protected MissionService missionService;

    @Autowired
    protected MissionBookmarkService missionBookmarkService;

    @Autowired
    protected MissionMatchService missionMatchService;

    @Autowired
    protected MissionMatchReader missionMatchReader;

    @Autowired
    protected MissionProposalService missionProposalService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MissionProposalRepository missionProposalRepository;

    @Autowired
    protected ReviewService reviewService;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ProfileService profileService;

    @Autowired
    protected UserImageRepository userImageRepository;

    @Autowired
    protected UserRegionRepository userRegionRepository;

    @Autowired
    protected UserMissionCategoryRepository userMissionCategoryRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected ChatRoomService chatRoomService;

    @Autowired
    protected MainService mainService;

    @Autowired
    protected MissionImageService missionImageService;

    @Autowired
    protected ReviewImageService reviewImageService;

    @Autowired
    protected ReviewImageRepository reviewImageRepository;

    @Autowired
    protected UserValidation userValidation;

    @MockBean
    protected S3ImageUploadService s3ImageUploadService;

    @MockBean
    protected S3ImageDeleteService s3ImageDeleteService;

    @MockBean
    protected MissionChatRoomRedisRepository missionChatRoomRedisRepository;

    @MockBean
    protected ChatMessageMongoRepository chatMessageMongoRepository;

    @Autowired
    protected UserQueryRepository userQueryRepository;

    @Autowired
    protected EntityManager em;

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
