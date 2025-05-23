package com.neocortex.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private LocalTime time;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ReminderDays> days;

    private boolean enabled;

    private LocalDateTime lastTriggered;
}
