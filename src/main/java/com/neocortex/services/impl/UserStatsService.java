package com.neocortex.services.impl;

import com.neocortex.payloads.user_stats.UserStatsResponse;
import com.neocortex.repositories.UserStatsRepository;
import com.neocortex.services.IUserStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatsService implements IUserStatsService {

    private final UserStatsRepository userStatsRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserStatsResponse getUserStatsByUserId(UUID userId) {
        log.info("Fetching user stats for userId: {}", userId);
        return userStatsRepository.findByUserId(userId)
                .map(stats ->{
                    log.info("Achievements: {}", stats.getAchievement());
                    log.info("User stats found for userId: {}", userId);

                    return modelMapper.map(stats, UserStatsResponse.class);
                })
                .orElseGet(() -> {
                    log.warn("No stats found for userId: {}", userId);
                    return UserStatsResponse.builder()
                            .userId(userId)
                            .resourcesUsed(0)
                            .achievement(new ArrayList<>())
                            .level(0)
                            .averageMood(0)
                            .currentStreak(0L)
                            .longestStreak(0L)
                            .moodCheckIns(0)
                            .journalEntries(0)
                            .build();
                });
    }

    @Override
    public void resetUserStats(UUID userId) {
        log.info("Resetting user stats for userId: {}", userId);
        userStatsRepository.findByUserId(userId).ifPresent(stats -> {
            stats.reset();
            userStatsRepository.save(stats);
            log.info("User stats reset for userId: {}", userId);
        });
    }

}
