package com.neocortex.payloads;

import com.neocortex.models.enums.ReminderDays;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReminderResponse {
    private Long id;
    private String title;
    private LocalTime time;
    private List<ReminderDays> days;
    private boolean enabled;
    private LocalDateTime lastTriggered;
    private UUID userId;
}