package com.neocortex.payloads;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJournalRequest {
    private String prompt;

    @NotBlank(message = "Mood is required")
    private String mood;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String attachmentURL;

    @NotBlank(message = "User ID is required")
    private UUID userId;
}