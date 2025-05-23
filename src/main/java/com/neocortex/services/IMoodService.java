package com.neocortex.services;

import com.neocortex.payloads.CreateMoodRequest;
import com.neocortex.payloads.UpdateMoodRequest;
import com.neocortex.payloads.MoodResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing mood entries within the application.
 * <p>
 * This interface defines the contract for mood-related operations, including creating, retrieving,
 * updating, and deleting mood entries associated with a user. Implementations of this interface
 * are responsible for handling the business logic for mood management.
 * </p>
 * <p>
 * Methods and their purposes:
 * <ul>
 *   <li><b>createMood</b>: Creates a new mood entry for a user.</li>
 *   <li><b>getMoodById</b>: Retrieves a specific mood entry by its ID for a user.</li>
 *   <li><b>getAllMoodsByUser</b>: Retrieves all mood entries for a specific user.</li>
 *   <li><b>updateMood</b>: Updates an existing mood entry for a user.</li>
 *   <li><b>deleteMood</b>: Deletes a specific mood entry for a user.</li>
 *   <li><b>deleteMultipleMoods</b>: Deletes multiple mood entries for a user.</li>
 * </ul>
 * </p>
 */
public interface IMoodService {

    /**
     * Creates a new mood entry for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param createMoodRequest the request payload containing mood details
     * @return the created {@link MoodResponse}
     */
    MoodResponse createMood(UUID userId, CreateMoodRequest createMoodRequest);

    /**
     * Retrieves a mood entry by its ID for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param moodId the unique identifier of the mood entry (Long)
     * @return the found {@link MoodResponse}
     */
    MoodResponse getMoodById(UUID userId, Long moodId);

    /**
     * Retrieves all mood entries for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @return a list of {@link MoodResponse} objects
     */
    List<MoodResponse> getAllMoodsByUser(UUID userId);

    /**
     * Updates an existing mood entry for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param moodId the unique identifier of the mood entry (Long)
     * @param updateMoodRequest the request payload containing updated mood details
     * @return the updated {@link MoodResponse}
     */
    MoodResponse updateMood(UUID userId, Long moodId, UpdateMoodRequest updateMoodRequest);

    /**
     * Deletes a specific mood entry for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param moodId the unique identifier of the mood entry (Long)
     */
    void deleteMood(UUID userId, Long moodId);

    /**
     * Deletes multiple mood entries for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param moodIds a list of mood entry IDs (Long) to be deleted
     */
    void deleteMultipleMoods(UUID userId, List<Long> moodIds);
}