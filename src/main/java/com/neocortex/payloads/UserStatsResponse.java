package com.neocortex.payloads;

import lombok.*;

import java.util.List;

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
    private Long id;
    private Integer journalEntries;
    private Integer moodCheckIns;
    private Integer resourcesUsed;
    private double averageMood;
    private Long longestStreak;
    private Long currentStreak;
    private Integer level;
    private List<String> achievements;
}