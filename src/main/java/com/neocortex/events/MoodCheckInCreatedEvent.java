package com.neocortex.events;

import lombok.Value;

import java.util.UUID;

/**
 * Event triggered when a user records a mood check-in.
 */
@Value
public class MoodCheckInCreatedEvent {
    UUID userId;
    double moodValue;
}
