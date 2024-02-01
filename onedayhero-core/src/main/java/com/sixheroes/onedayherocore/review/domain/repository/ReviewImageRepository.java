package com.sixheroes.onedayherocore.review.domain.repository;

import com.sixheroes.onedayherocore.review.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    @Query("select ri from ReviewImage ri where ri.review.id = :reviewId")
    Optional<List<ReviewImage>> findByReviewId(Long reviewId);
}
