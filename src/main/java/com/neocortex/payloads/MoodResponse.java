package com.neocortex.payloads;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private String location;
}