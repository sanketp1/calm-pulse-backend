package com.neocortex.events;

import lombok.Value;

import java.util.UUID;

/**
 * Event triggered when a journal entry is created by the user.
 */
@Value
public class JournalCreatedEvent {
    UUID userId;
}
