package com.sixheroes.onedayherocore.review.domain.repository;

import com.sixheroes.onedayherocore.review.domain.repository.dto.ReviewCreateEventDto;
import com.sixheroes.onedayherocore.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
        select 
            new com.sixheroes.onedayherocore.review.domain.repository.dto.ReviewCreateEventDto(
                r.receiverId, u.userBasicInfo.nickname, r.id, r.missionTitle
            )
        from Review r
        join User u on u.id = r.senderId
        where r.id = :reviewId
    """)
    Optional<ReviewCreateEventDto> findReviewCreateEventById(
        @Param("reviewId") Long reviewId
    );
}
