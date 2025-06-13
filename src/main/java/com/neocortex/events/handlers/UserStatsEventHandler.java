package com.neocortex.events.handlers;

import com.neocortex.events.*;
import com.neocortex.services.IUserStatsDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStatsEventHandler {

    private final IUserStatsDomainService userStatsDomainService;

    @Async
    @EventListener
    public void handleJournalCreatedEvent(JournalCreatedEvent event) {
        log.info("Received JournalCreatedEvent for user: {}", event.getUserId());
        userStatsDomainService.handleJournalCreated(event.getUserId());
    }

    @Async
    @EventListener
    public void handleMoodCheckInEvent(MoodCheckInCreatedEvent event) {
        log.info("Received MoodCheckInCreatedEvent for user: {} with mood value: {}", event.getUserId(), event.getMoodValue());
        userStatsDomainService.handleMoodCheckInCreated(event.getUserId(), event.getMoodValue());
    }

    @Async
    @EventListener
    public void handleResourceUsed(ResourceUsedEvent event) {
        log.info("Received ResourceUsedEvent for user: {}", event.getUserId());
        userStatsDomainService.handleResourceUsed(event.getUserId());
    }

    @Async
    @EventListener
    public void handleUserStatsResetEvent(UserStatsResetEvent event) {
        log.info("Received UserStatsResetEvent for user: {}", event.getUserId());
        userStatsDomainService.resetStats(event.getUserId());
    }
}
