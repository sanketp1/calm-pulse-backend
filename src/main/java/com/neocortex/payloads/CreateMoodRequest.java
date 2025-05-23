package com.neocortex.payloads;

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
    @NotBlank(message = "Mood type is required")
    private String moodType;

    private Integer value;

    private String note;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timeStamp;

    private String location;

}