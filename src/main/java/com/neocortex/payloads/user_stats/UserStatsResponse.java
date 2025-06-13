package com.neocortex.payloads.user_stats;

import com.neocortex.models.embeddables.Achievements;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for user statistics.
 * <p>
 * Encapsulates statistical data and achievements for a user.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatsResponse {
    private UUID userId;
    private Integer journalEntries;
    private Integer moodCheckIns;
    private Integer resourcesUsed;
    private double averageMood;
    private Long longestStreak;
    private Long currentStreak;
    private Integer level;
    private List<Achievements> achievement;
}