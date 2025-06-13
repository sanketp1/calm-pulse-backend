package com.neocortex.services;

import java.util.UUID;

/**
 * Abstraction for the domain service handling user statistics operations.
 * Exposes business-related operations such as journal entries, mood check-ins,
 * resource usage, and reset functionality, ensuring decoupling from implementation.
 */
public interface IUserStatsDomainService {

    /**
     * Handles updating stats when a user creates a journal entry.
     *
     * @param userId UUID of the user
     */
    void handleJournalCreated(UUID userId);

    /**
     * Handles updating stats when a user performs a mood check-in.
     *
     * @param userId UUID of the user
     * @param moodValue mood value submitted by the user
     */
    void handleMoodCheckInCreated(UUID userId, double moodValue);

    /**
     * Handles updating stats when a user uses a resource.
     *
     * @param userId UUID of the user
     */
    void handleResourceUsed(UUID userId);

    /**
     * Resets all statistics for the given user.
     *
     * @param userId UUID of the user
     */
    void resetStats(UUID userId);
}
