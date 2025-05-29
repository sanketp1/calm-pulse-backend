package com.neocortex.payloads;

import com.neocortex.models.embeddables.Attachment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JournalResponse {
    private Long id;
    private String prompt;
    private MoodResponse mood;
    private String title;
    private String description;
    private Attachment attachment;
    private LocalDateTime createdAt;
    private UUID userId;
}