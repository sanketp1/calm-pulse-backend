package com.neocortex.payloads;

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
    private String mood;
    private String title;
    private String description;
    private String attachmentURL;
    private LocalDateTime timeStamp;
    private UUID userId;
}