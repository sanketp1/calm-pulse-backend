package com.neocortex.services;

import com.neocortex.payloads.UserStatsResponse;

import java.util.UUID;

/**
 * Service interface for managing user statistics in the application.
 * <p>
 * This interface defines methods for retrieving and resetting user statistics.
 * It provides a contract for user stats management functionality within the system.
 * </p>
 * <p>Key methods:</p>
 * <ul>
 *   <li><b>getUserStatsByUserId</b>: Retrieves statistics for a specific user.</li>
 *   <li><b>resetUserStats</b>: Resets statistics for a specific user.</li>
 * </ul>
 */
public interface IUserStatsService {

    /**
     * Retrieves statistics for a specific user by their unique identifier.
     *
     * @param userId the unique identifier of the user
     * @return a {@link UserStatsResponse} object containing the user's statistics
     */
    UserStatsResponse getUserStatsByUserId(UUID userId);

    /**
     * Resets statistics for a specific user by their unique identifier.
     *
     * @param userId the unique identifier of the user
     */
    void resetUserStats(UUID userId);
}