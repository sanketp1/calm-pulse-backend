package com.neocortex.services;

import com.neocortex.payloads.CreateJournalRequest;
import com.neocortex.payloads.UpdateJournalRequest;
import com.neocortex.payloads.JournalResponse;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing journal entries within the application.
 * <p>
 * Provides methods for creating, retrieving, updating, and deleting journals associated with a user.
 * </p>
 * <p>
 * Fields and their purposes:
 * <ul>
 *   <li><b>createJournal</b>: Creates a new journal entry for a user.</li>
 *   <li><b>getJournalById</b>: Retrieves a specific journal entry by its ID for a user.</li>
 *   <li><b>getAllJournalsByUser</b>: Retrieves all journal entries for a specific user.</li>
 *   <li><b>updateJournal</b>: Updates an existing journal entry for a user.</li>
 *   <li><b>deleteJournal</b>: Deletes a specific journal entry for a user.</li>
 *   <li><b>deleteMultipleJournals</b>: Deletes multiple journal entries for a user.</li>
 * </ul>
 * </p>
 */
public interface IJournalService {

    /**
     * Creates a new journal entry for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param createJournalRequest the request payload containing journal details
     * @return the created {@link JournalResponse}
     */
    JournalResponse createJournal(UUID userId, CreateJournalRequest createJournalRequest);

    /**
     * Retrieves a journal entry by its ID for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param journalId the unique identifier of the journal entry (Long)
     * @return the found {@link JournalResponse}
     */
    JournalResponse getJournalById(UUID userId, Long journalId);

    /**
     * Retrieves all journal entries for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @return a list of {@link JournalResponse} objects
     */
    List<JournalResponse> getAllJournalsByUser(UUID userId);

    /**
     * Updates an existing journal entry for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param journalId the unique identifier of the journal entry (Long)
     * @param updateJournalRequest the request payload containing updated journal details
     * @return the updated {@link JournalResponse}
     */
    JournalResponse updateJournal(UUID userId, Long journalId, UpdateJournalRequest updateJournalRequest);

    /**
     * Deletes a specific journal entry for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param journalId the unique identifier of the journal entry (Long)
     */
    void deleteJournal(UUID userId, Long journalId);

    /**
     * Deletes multiple journal entries for the specified user.
     *
     * @param userId the unique identifier of the user (UUID)
     * @param journalIds a list of journal entry IDs (Long) to be deleted
     */
    void deleteMultipleJournals(UUID userId, List<Long> journalIds);
}