package com.neocortex.events;

import lombok.Value;

import java.util.UUID;

/**
 * Event triggered when a user uses a resource.
 */
@Value
public class ResourceUsedEvent {
    UUID userId;
}
