package com.sixheroes.onedayherocore.review.application;

import com.sixheroes.onedayherocore.global.s3.S3ImageDeleteService;
import com.sixheroes.onedayherocore.review.domain.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sixheroes.onedayherocore.review.application.ReviewService.s3DeleteRequestMapper;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewImageService {

    private final ReviewImageReader reviewImageReader;
    private final ReviewImageRepository reviewImageRepository;
    private final S3ImageDeleteService s3ImageDeleteService;

    @Transactional
    public void delete(
            Long userId,
            Long reviewImageId
    ) {
        var reviewImage = reviewImageReader.findById(reviewImageId);
        reviewImage.validOwn(userId);

        reviewImageRepository.delete(reviewImage);
        s3ImageDeleteService.deleteImages(List.of(s3DeleteRequestMapper.apply(reviewImage)));
    }
}
