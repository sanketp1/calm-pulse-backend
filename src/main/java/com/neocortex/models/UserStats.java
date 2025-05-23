package com.neocortex.models;

import com.neocortex.models.embeddables.Achievements;
import jakarta.persistence.*;

import java.util.List;

/**
 * Represents statistical data and achievements for a user in the application.
 * <p>
 * The UserStats entity aggregates various metrics related to a user's activity,
 * progress, and engagement, such as journal entries, mood check-ins, resource usage,
 * mood averages, streaks, level, and achievements. It is linked to a single user
 * and is used for analytics, gamification, and personalized feedback.
 * </p>
 *
 * <ul>
 *   <li><b>id</b>: Unique identifier for the user stats entry (Long).</li>
 *   <li><b>journalEntries</b>: Number of journal entries created by the user (Integer).</li>
 *   <li><b>moodCheckIns</b>: Number of mood check-ins recorded by the user (Integer).</li>
 *   <li><b>resourcesUsed</b>: Number of resources used by the user (Integer).</li>
 *   <li><b>averageMood</b>: Average mood value calculated from mood check-ins (double).</li>
 *   <li><b>longestStreak</b>: Longest streak of consecutive activity days (Long).</li>
 *   <li><b>currentStreak</b>: Current streak of consecutive activity days (Long).</li>
 *   <li><b>level</b>: User's current level in the application (Integer).</li>
 *   <li><b>achievement</b>: List of achievements earned by the user (List&lt;Achievements&gt;).</li>
 *   <li><b>user</b>: Reference to the associated user (User, one-to-one relationship).</li>
 * </ul>
 */
@Entity
@Table(name = "user_stats")
public class UserStats {

    /**
     * Unique identifier for the user stats entry.
     * Type: Long
     * Primary key, auto-generated.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Number of journal entries created by the user.
     * Type: Integer
     */
    private Integer journalEntries;

    /**
     * Number of mood check-ins recorded by the user.
     * Type: Integer
     */
    private Integer moodCheckIns;

    /**
     * Number of resources used by the user.
     * Type: Integer
     */
    private Integer resourcesUsed;

    /**
     * Average mood value calculated from the user's mood check-ins.
     * Type: double
     */
    private double averageMood;

    /**
     * Longest streak of consecutive days with activity.
     * Type: Long
     */
    private Long longestStreak;

    /**
     * Current streak of consecutive days with activity.
     * Type: Long
     */
    private Long currentStreak;

    /**
     * User's current level in the application.
     * Type: Integer
     */
    private Integer level;

    /**
     * List of achievements earned by the user.
     * Type: List&lt;Achievements&gt;
     * Stored as an element collection.
     */
    @ElementCollection
    private List<Achievements> achievement;

    /**
     * Reference to the associated user.
     * Type: User
     * One-to-one relationship; not nullable.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}