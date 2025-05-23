package com.neocortex.models;

import com.neocortex.models.enums.ReminderDays;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Represents a reminder entity for scheduling notifications or alerts for a user.
 * <p>
 * The Reminder entity is used to persist user-defined reminders, including the time,
 * days of the week, status, and the user to whom the reminder belongs. It supports
 * recurring reminders on specific days and tracks when the reminder was last triggered.
 * </p>
 *
 * <ul>
 *   <li><b>id</b>: Unique identifier for the reminder (Long).</li>
 *   <li><b>title</b>: The title or description of the reminder (String).</li>
 *   <li><b>time</b>: The time of day when the reminder should trigger (LocalTime).</li>
 *   <li><b>days</b>: List of days of the week when the reminder is active (List&lt;ReminderDays&gt;).</li>
 *   <li><b>enabled</b>: Indicates if the reminder is currently active (boolean).</li>
 *   <li><b>lastTriggered</b>: Timestamp of the last time the reminder was triggered (LocalDateTime).</li>
 *   <li><b>user</b>: The user who owns this reminder (User, many-to-one relationship).</li>
 * </ul>
 */
@Entity
@Table(name = "reminders")
public class Reminder {

    /**
     * Unique identifier for the reminder.
     * Type: Long
     * Primary key, auto-generated.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The title or description of the reminder.
     * Type: String
     * Optional field.
     */
    private String title;

    /**
     * The time of day when the reminder should trigger.
     * Type: LocalTime
     * Required field.
     */
    private LocalTime time;

    /**
     * List of days of the week when the reminder is active.
     * Type: List&lt;ReminderDays&gt;
     * Stored as strings in a separate collection table.
     */
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ReminderDays> days;

    /**
     * Indicates if the reminder is currently enabled.
     * Type: boolean
     * If false, the reminder will not trigger.
     */
    private boolean enabled;

    /**
     * Timestamp of the last time the reminder was triggered.
     * Type: LocalDateTime
     * Optional field.
     */
    private LocalDateTime lastTriggered;

    /**
     * The user who owns this reminder.
     * Type: User
     * Many-to-one relationship; not nullable.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}