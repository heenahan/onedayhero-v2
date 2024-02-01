package com.sixheroes.onedayherocore.missionproposal.application.event;

import com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalApproveEvent;
import com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalCreateEvent;
import com.sixheroes.onedayherocore.missionproposal.application.event.dto.MissionProposalRejectEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class MissionProposalEventListener {

    private final MissionProposalEventService missionProposalEventService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyMissionProposalCreate(
            MissionProposalCreateEvent missionProposalCreateEvent
    ) {
        missionProposalEventService.notifyMissionProposalCreate(missionProposalCreateEvent);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyMissionProposalApprove(
            MissionProposalApproveEvent missionProposalApproveEvent
    ) {
        missionProposalEventService.notifyMissionProposalApprove(missionProposalApproveEvent);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyMissionProposalReject(
            MissionProposalRejectEvent missionProposalRejectEvent
    ) {
        missionProposalEventService.notifyMissionProposalReject(missionProposalRejectEvent);
    }
}