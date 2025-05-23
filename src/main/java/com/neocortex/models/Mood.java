package com.neocortex.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "moods")
public class Mood {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private MoodType moodType;

    private Integer value;

    private String note;

    private LocalDateTime timeStamp;

    @Embedded
    private Location location;
}
