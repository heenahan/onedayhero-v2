package com.sixheroes.onedayherocore.event.domain.repository;

import com.sixheroes.onedayherocore.event.domain.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Events, Long> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Events save(Events events);

    @Query("""
        select e
        from Events e
        where e.eventType = 'ALARM' and e.success = false and e.createdAt <= :time
    """)
    List<Events> findAllBySuccessIsFalse(
        @Param("time")LocalDateTime time
    );
}
