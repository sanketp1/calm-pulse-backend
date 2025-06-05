package com.neocortex.payloads.mood;

import com.neocortex.models.embeddables.Location;
import com.neocortex.models.enums.MoodType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMoodRequest {
    private MoodType moodType;
    private Integer value;
    private String note;
    private Location location;
}