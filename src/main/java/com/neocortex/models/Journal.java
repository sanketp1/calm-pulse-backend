package com.neocortex.models;

import com.neocortex.models.embeddables.Attachment;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

/**
 * Represents a journal entry created by a user in the application.
 * <p>
 * The Journal entity stores information about a user's journaling activity,
 * including the prompt, mood association, title, description, attachments, timestamp,
 * and the user who created the entry. It is used to persist and manage user-generated
 * journal content within the system.
 * </p>
 *
 * <ul>
 *   <li><b>id</b> \(`Long`\): Unique identifier for the journal entry.</li>
 *   <li><b>prompt</b> \(`String`\): The prompt or question that inspired the journal entry.</li>
 *   <li><b>mood</b> \(`Mood`\): The mood associated with this journal entry; one-to-one relationship.</li>
 *   <li><b>title</b> \(`String`\): The title of the journal entry.</li>
 *   <li><b>description</b> \(`String`\): The main content or body of the journal entry.</li>
 *   <li><b>attachment</b> \(`Attachment`\): Embedded attachment (e.g., image, file) related to the entry.</li>
 *   <li><b>timeStamp</b> \(`LocalDateTime`\): The date and time when the journal entry was created.</li>
 *   <li><b>user</b> \(`User`\): The user who created this journal entry; many-to-one relationship.</li>
 * </ul>
 */
@Entity
@Table(name = "journals")
public class Journal {

    /**
     * Unique identifier for the journal entry.
     * Type: Long
     * Primary key, auto-generated.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The prompt or question that inspired the journal entry.
     * Type: String
     * Optional field.
     */
    private String prompt;

    /**
     * The mood associated with this journal entry.
     * Type: Mood
     * One-to-one relationship; may be null if not linked to a mood.
     */
    @OneToOne
    @JoinColumn(name = "mood_id", referencedColumnName = "id")
    private Mood mood;

    /**
     * The title of the journal entry.
     * Type: String
     * Optional field.
     */
    private String title;

    /**
     * The main content or body of the journal entry.
     * Type: String
     * Optional field.
     */
    private String description;

    /**
     * Embedded attachment (e.g., image, file) related to the entry.
     * Type: Attachment
     * Optional field.
     */
    @Embedded
    private Attachment attachment;

    /**
     * The date and time when the journal entry was created.
     * Type: LocalDateTime
     * Optional field.
     */
    private LocalDateTime timeStamp;

    /**
     * The user who created this journal entry.
     * Type: User
     * Many-to-one relationship; not nullable.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}