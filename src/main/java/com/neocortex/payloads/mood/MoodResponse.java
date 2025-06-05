package com.neocortex.payloads.mood;

import com.neocortex.models.embeddables.Location;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoodResponse {
    private Long id;
    private String moodType;
    private Integer value;
    private String note;
    private LocalDateTime timeStamp;
    private Location location;
}