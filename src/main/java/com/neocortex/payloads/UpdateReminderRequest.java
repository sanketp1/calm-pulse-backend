package com.neocortex.payloads;

import com.neocortex.models.enums.ReminderDays;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReminderRequest {
    private String title;
    private LocalTime time;
    private List<ReminderDays> days;
    private boolean enabled;
}