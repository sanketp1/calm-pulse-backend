package com.neocortex.services;

import com.neocortex.payloads.CreateReminderRequest;
import com.neocortex.payloads.UpdateReminderRequest;
import com.neocortex.payloads.ReminderResponse;

import java.util.List;
import java.util.UUID;

/**
 * Interface for managing reminders in the application.
 * <p>
 * This service provides methods to create, retrieve, update, and delete reminders
 * for a specific user. It also supports operations for managing multiple reminders
 * at once. Each method ensures that the operations are scoped to the provided user ID.
 * </p>
 *
 * <p>Methods:</p>
 * <ul>
 *   <li><b>createReminder</b>: Creates a new reminder for a user.</li>
 *   <li><b>getReminderById</b>: Retrieves a specific reminder by its ID.</li>
 *   <li><b>getAllRemindersByUser</b>: Fetches all reminders associated with a user.</li>
 *   <li><b>updateReminder</b>: Updates an existing reminder for a user.</li>
 *   <li><b>deleteReminder</b>: Deletes a specific reminder by its ID.</li>
 *   <li><b>deleteMultipleReminders</b>: Deletes multiple reminders by their IDs.</li>
 * </ul>
 */
public interface IReminderService {

    /**
     * Creates a new reminder for the specified user.
     *
     * @param userId                The unique identifier of the user creating the reminder.
     * @param createReminderRequest The request object containing the details of the reminder to be created.
     * @return A {@link ReminderResponse} object containing the details of the created reminder.
     */
    ReminderResponse createReminder(UUID userId, CreateReminderRequest createReminderRequest);

    /**
     * Retrieves a specific reminder by its ID for the specified user.
     *
     * @param userId     The unique identifier of the user.
     * @param reminderId The unique identifier of the reminder to retrieve.
     * @return A {@link ReminderResponse} object containing the details of the retrieved reminder.
     */
    ReminderResponse getReminderById(UUID userId, Long reminderId);

    /**
     * Fetches all reminders associated with the specified user.
     *
     * @param userId The unique identifier of the user.
     * @return A list of {@link ReminderResponse} objects containing the details of all reminders for the user.
     */
    List<ReminderResponse> getAllRemindersByUser(UUID userId);

    /**
     * Updates an existing reminder for the specified user.
     *
     * @param userId                The unique identifier of the user.
     * @param reminderId            The unique identifier of the reminder to update.
     * @param updateReminderRequest The request object containing the updated details of the reminder.
     * @return A {@link ReminderResponse} object containing the details of the updated reminder.
     */
    ReminderResponse updateReminder(UUID userId, Long reminderId, UpdateReminderRequest updateReminderRequest);

    /**
     * Deletes a specific reminder by its ID for the specified user.
     *
     * @param userId     The unique identifier of the user.
     * @param reminderId The unique identifier of the reminder to delete.
     */
    void deleteReminder(UUID userId, Long reminderId);

    /**
     * Deletes multiple reminders by their IDs for the specified user.
     *
     * @param userId      The unique identifier of the user.
     * @param reminderIds A list of unique identifiers of the reminders to delete.
     */
    void deleteMultipleReminders(UUID userId, List<Long> reminderIds);
}