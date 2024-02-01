package com.sixheroes.onedayherocore.review.application.event;

import com.sixheroes.onedayherocore.review.application.event.dto.ReviewCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ReviewEventListener {

    private final ReviewEventService reviewEventService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyReviewCreate(
        ReviewCreateEvent reviewCreateEvent
    ) {
        reviewEventService.notifyReviewCreate(reviewCreateEvent);
    }
}
