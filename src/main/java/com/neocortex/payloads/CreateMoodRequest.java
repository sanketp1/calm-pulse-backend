package com.neocortex.payloads;

import com.neocortex.models.embeddables.Location;
import com.neocortex.models.enums.MoodType;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

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