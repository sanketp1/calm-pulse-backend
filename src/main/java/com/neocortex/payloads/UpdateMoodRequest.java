package com.neocortex.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMoodRequest {
    private String moodType;
    private Integer value;
    private String note;
    private String location;
}