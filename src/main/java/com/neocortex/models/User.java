package com.neocortex.models;

import com.neocortex.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Represents an application user and their profile, authentication, and relationships.
 * <p>
 * The User entity contains personal information, authentication credentials,
 * and associations to journals, moods, and reminders. It is the core entity
 * for user management and authorization in the system.
 * </p>
 *
 * <ul>
 *   <li><b>id</b>: Unique identifier for the user (UUID).</li>
 *   <li><b>name</b>: User's display name.</li>
 *   <li><b>email</b>: User's email address (unique, required for login).</li>
 *   <li><b>password</b>: Hashed password for authentication (required).</li>
 *   <li><b>avatar</b>: URL or path to the user's profile image.</li>
 *   <li><b>phoneNumber</b>: User's phone number for contact (optional).</li>
 *   <li><b>address</b>: User's address for contact (optional).</li>
 *   <li><b>joinedDate</b>: Timestamp when the user registered.</li>
 *   <li><b>lastLogin</b>: Timestamp of the user's last login.</li>
 *   <li><b>lastUpdated</b>: Timestamp of the last profile update.</li>
 *   <li><b>role</b>: User's role (e.g., USER, ADMIN) for authorization.</li>
 *   <li><b>journals</b>: List of journal entries created by the user.</li>
 *   <li><b>moods</b>: List of mood check-ins associated with the user.</li>
 *   <li><b>reminders</b>: List of reminders set by the user.</li>
 * </ul>
 *
 * @author sanketp1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    /**
     * Unique identifier for the user (UUID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The user's display name.
     */
    private String name;

/**
     * The user's phone number.
     * Optional field for contact information.
     */
    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    /**
     * The user's address.
     * Optional field for additional contact details.
     */
    @Column(name = "address", nullable = true)
    private String address;
    /**
     * The user's email address. Must be unique and not null.
     * Used for login and communication.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * The user's hashed password. Not null.
     * Used for authentication.
     */
    @Column(nullable = false)
    private String password;

    /**
     * URL or path to the user's avatar image.
     * Optional field for profile customization.
     */
    private String avatar;

    /**
     * The date and time when the user registered.
     */
    private LocalDateTime joinedDate;

    /**
     * The date and time of the user's last login.
     */
    private LocalDateTime lastLogin;

    /**
     * The date and time when the user's profile was last updated.
     */
    private LocalDateTime lastUpdated;

    /**
     * The user's role in the system (e.g., USER, ADMIN).
     * Determines access level and permissions.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * List of journals associated with the user.
     * Represents the user's journal entries.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Journal> journals;

    /**
     * List of mood entries associated with the user.
     * Represents the user's mood check-ins.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mood> moods;

    /**
     * List of reminders set by the user.
     * Used for scheduling notifications or tasks.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminders;
}