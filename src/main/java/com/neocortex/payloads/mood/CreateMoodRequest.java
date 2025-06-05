package com.neocortex.payloads.mood;

import com.neocortex.models.embeddables.Location;
import com.neocortex.models.enums.MoodType;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMoodRequest {
   @NotNull(message = "Mood type is required")
   private MoodType moodType;

    private Integer value;

    private String note;


    private Location location;

}