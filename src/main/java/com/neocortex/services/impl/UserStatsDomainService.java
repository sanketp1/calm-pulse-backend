package com.neocortex.domain;

import com.neocortex.models.UserStats;
import com.neocortex.repositories.UserStatsRepository;
import com.neocortex.services.IUserStatsDomainService;
import com.neocortex.services.impl.AchievementEvaluatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * Domain service implementation for user statistics management.
 * Handles the business logic and event-based updates to the user's statistics.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatsDomainService implements IUserStatsDomainService {

    private final UserStatsRepository userStatsRepository;
    private final AchievementEvaluatorService evaluatorService;

    @Override
    public void handleJournalCreated(UUID userId) {
        log.info("Handling journal creation for user: {}", userId);
        UserStats stats = getOrCreateUserStats(userId);
        stats.setJournalEntries(stats.getJournalEntries() + 1);
        updateStreaks(stats);
        evaluatorService.evaluate(stats);
        userStatsRepository.save(stats);
    }

    @Override
    public void handleMoodCheckInCreated(UUID userId, double moodValue) {
        log.info("Handling mood check-in for user: {} with mood value: {}", userId, moodValue);
        UserStats stats = getOrCreateUserStats(userId);
        int newCount = stats.getMoodCheckIns() + 1;
        stats.setAverageMood((stats.getAverageMood() * stats.getMoodCheckIns() + moodValue) / newCount);
        stats.setMoodCheckIns(newCount);
        updateStreaks(stats);
        evaluatorService.evaluate(stats);
        userStatsRepository.save(stats);
    }

    @Override
    public void handleResourceUsed(UUID userId) {
        log.info("Handling resource usage for user: {}", userId);
        UserStats stats = getOrCreateUserStats(userId);
        stats.setResourcesUsed(stats.getResourcesUsed() + 1);
        updateStreaks(stats);
        evaluatorService.evaluate(stats);
        userStatsRepository.save(stats);
    }

    @Override
    public void resetStats(UUID userId) {
        log.info("Resetting stats for user: {}", userId);
        UserStats stats = getOrCreateUserStats(userId);
        stats.reset();
        stats.setAchievement(new ArrayList<>());
        userStatsRepository.save(stats);
    }

    private void updateStreaks(UserStats stats) {
        // Sample streak logic for demo purposes. You can replace with real date-based checks.
        stats.setCurrentStreak(stats.getCurrentStreak() + 1);
        if (stats.getCurrentStreak() > stats.getLongestStreak()) {
            stats.setLongestStreak(stats.getCurrentStreak());
        }
        // Update level arbitrarily (e.g., every 10 actions = 1 level up)
        int level = 1 + (stats.getJournalEntries() + stats.getMoodCheckIns() + stats.getResourcesUsed()) / 10;
        stats.setLevel(level);
    }

    private UserStats getOrCreateUserStats(UUID userId) {
        Optional<UserStats> optionalStats = userStatsRepository.findByUserId(userId);
        return optionalStats.orElseGet(() -> {
            log.info("Creating new UserStats entry for user: {}", userId);
            UserStats stats = UserStats.builder()
                    .journalEntries(0)
                    .moodCheckIns(0)
                    .resourcesUsed(0)
                    .averageMood(0)
                    .longestStreak(0L)
                    .currentStreak(0L)
                    .level(1)
                    .achievement(new ArrayList<>())
                    .user(com.neocortex.models.User.builder().id(userId).build())
                    .build();
            return userStatsRepository.save(stats);
        });
    }
}
