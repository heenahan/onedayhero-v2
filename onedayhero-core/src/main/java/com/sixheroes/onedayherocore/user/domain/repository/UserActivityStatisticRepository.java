package com.sixheroes.onedayherocore.user.domain.repository;

import com.sixheroes.onedayherocore.user.domain.UserActivityStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityStatisticRepository extends JpaRepository<UserActivityStatistic, Long> {
}
