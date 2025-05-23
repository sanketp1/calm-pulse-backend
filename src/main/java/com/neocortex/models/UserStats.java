package com.neocortex.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_stats")
public class UserStats {

    @Id
    @GeneratedValue
    private Long id;
    private Integer journalEntries;
    private Integer moodCheckIns;
    private Integer resourcesUsed;
    private double averageMood;
    private Long longestStreak;
    private Long currentStreak;
    private Integer level;

    @Embedded
    private List<Achievements> achievement;

}
