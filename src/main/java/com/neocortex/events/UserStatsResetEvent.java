package com.neocortex.events;

import lombok.Value;

import java.util.UUID;

/**
 * Event triggered when the user's statistics are reset manually or automatically.
 */
@Value
public class UserStatsResetEvent {
    UUID userId;
}
