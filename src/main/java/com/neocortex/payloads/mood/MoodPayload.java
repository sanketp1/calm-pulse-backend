package com.neocortex.payloads.mood;

import com.neocortex.models.embeddables.Location;
import com.neocortex.models.enums.MoodType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoodPayload {
    private Long id; // Needed for update to identify the mood entry
    private MoodType moodType;
    private Integer value;
    private String note;
    private LocalDateTime timeStamp;
    private Location location;
}
