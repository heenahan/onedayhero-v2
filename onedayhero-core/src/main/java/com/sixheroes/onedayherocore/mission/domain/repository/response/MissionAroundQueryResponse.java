package com.sixheroes.onedayherocore.mission.domain.repository.response;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MissionAroundQueryResponse {

    Long getId();

    Long getMissionCategoryId();

    String getMissionCategoryCode();

    String getMissionCategoryName();

    Long getRegionId();

    String getSi();

    String getGu();

    String getDong();

    String getTitle();

    String getLocation();

    LocalDate getMissionDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    Integer getPrice();
}
