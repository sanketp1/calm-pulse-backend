package com.neocortex.payloads;

import com.neocortex.models.enums.ReminderDays;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReminderRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Time is required")
    private LocalTime time;

    @NotNull(message = "Days are required")
    private List<ReminderDays> days;

    private boolean enabled;

    @NotNull(message = "User ID is required")
    private UUID userId;
}