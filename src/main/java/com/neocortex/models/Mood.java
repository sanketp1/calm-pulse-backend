package com.neocortex.models;

import com.neocortex.models.embeddables.Location;
import com.neocortex.models.enums.MoodType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents a mood entry recorded by a user in the application.
 * <p>
 * The Mood entity captures a user's emotional state at a specific time and location,
 * along with optional notes and a reference to the user who created the entry.
 * It is used to track mood trends, analyze emotional well-being, and associate moods
 * with other entities such as journals.
 * </p>
 *
 * <ul>
 *   <li><b>id</b>: Unique identifier for the mood entry (Long).</li>
 *   <li><b>moodType</b>: The type of mood (MoodType enum) selected by the user.</li>
 *   <li><b>value</b>: Numeric value representing the intensity or rating of the mood (Integer).</li>
 *   <li><b>note</b>: Optional note or description about the mood (String).</li>
 *   <li><b>timeStamp</b>: Date and time when the mood was recorded (LocalDateTime).</li>
 *   <li><b>location</b>: Embedded location information where the mood was recorded (Location).</li>
 *   <li><b>user</b>: Reference to the user who created the mood entry (User, many-to-one relationship).</li>
 * </ul>
 */
@Entity
@Table(name = "moods")
public class Mood {

    /**
     * Unique identifier for the mood entry.
     * Type: Long
     * Primary key, auto-generated.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The type of mood selected by the user.
     * Type: MoodType (enum)
     * Stored as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    private MoodType moodType;

    /**
     * Numeric value representing the intensity or rating of the mood.
     * Type: Integer
     * Optional field.
     */
    private Integer value;

    /**
     * Optional note or description about the mood.
     * Type: String
     */
    private String note;

    /**
     * Date and time when the mood was recorded.
     * Type: LocalDateTime
     */
    private LocalDateTime timeStamp;

    /**
     * Embedded location information where the mood was recorded.
     * Type: Location (embeddable)
     * Optional field.
     */
    @Embedded
    private Location location;

    /**
     * Reference to the user who created the mood entry.
     * Type: User
     * Many-to-one relationship; not nullable.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}