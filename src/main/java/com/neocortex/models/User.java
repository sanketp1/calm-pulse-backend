package com.neocortex.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
     * Represents a user entity in the system.
     *
     * <p>
     * This class is mapped to the `users` table in the database and contains
     * user-related information such as name, email, password, avatar, roles, and timestamps.
     * </p>
     *
     * <p>
     * Fields:
     * <ul>
     *   <li><b>id</b>: Unique identifier for the user (UUID, primary key).</li>
     *   <li><b>name</b>: Full name of the user.</li>
     *   <li><b>email</b>: Unique email address of the user (required).</li>
     *   <li><b>password</b>: Hashed password for authentication (required).</li>
     *   <li><b>avatar</b>: URL or path to the user's avatar image.</li>
     *   <li><b>isAdmin</b>: Flag indicating if the user has admin privileges.</li>
     *   <li><b>joinedDate</b>: Date and time when the user registered.</li>
     *   <li><b>lastLogin</b>: Date and time of the user's last login.</li>
     *   <li><b>lastUpdated</b>: Date and time when the user's profile was last updated.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Example usage:
     * <pre>
     *     User user = new User();
     *     user.setName("John Doe");
     *     user.setEmail("john.doe@example.com");
     * </pre>
     * </p>
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Entity
    @Table(name = "users")
    public class User {

        /** Unique identifier for the user. */
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        /** Full name of the user. */
        private String name;

        /** Unique email address of the user. */
        @Column(unique = true, nullable = false)
        private String email;

        /** Hashed password for authentication. */
        @Column(nullable = false)
        private String password;

        /** URL or path to the user's avatar image. */
        private String avatar;

        /** Flag indicating if the user has admin privileges. */
        private boolean isAdmin = false;

        /** Date and time when the user registered. */
        private LocalDateTime joinedDate;

        /** Date and time of the user's last login. */
        private LocalDateTime lastLogin;

        /** Date and time when the user's profile was last updated. */
        private LocalDateTime lastUpdated;

//        @OneToOne(cascade = CascadeType.ALL)
//        @JoinColumn(name = "stats_id")
//        private UserStats stats;

}