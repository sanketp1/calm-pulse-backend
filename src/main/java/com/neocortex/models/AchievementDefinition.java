package com.neocortex.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "achievement_definitions")
public class AchievementDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; // UUID string

    private String title;

    private String description;

    /**
     * Criteria expression, e.g. "journalEntries >= 10"
     */
    private String criteriaExpression;

    private String tag;
}
