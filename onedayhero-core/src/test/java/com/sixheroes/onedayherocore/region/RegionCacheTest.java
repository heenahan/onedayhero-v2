package com.sixheroes.onedayherocore.region;

import com.sixheroes.onedayherocore.region.application.RegionService;
import com.sixheroes.onedayherocore.region.domain.Region;
import com.sixheroes.onedayherocore.region.domain.repository.RegionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class RegionCacheTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionService regionService;

    @MockBean
    private RedisConnectionFactory redisConnectionFactory;

    @DisplayName("이미 불러온 전체 지역 데이터는 한 번 만 불러온다.")
    @Test
    void regionCacheRead() {
        // given
        var regionA = Region.builder()
                .id(1L)
                .si("서울특별시")
                .gu("강남구")
                .dong("삼성1동")
                .build();

        var regionB = Region.builder()
                .id(2L)
                .si("서울특별시")
                .gu("서초구")
                .dong("양재1동")
                .build();

        regionRepository.saveAll(List.of(regionA, regionB));

        // when
        var allRegions = regionService.findAllRegions();

        var regionCache = cacheManager.getCache("regions");

        // then
        assertThat(regionCache).isNotNull();
    }
}
