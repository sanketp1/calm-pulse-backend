package com.neocortex.payloads.journal;

import com.neocortex.models.embeddables.Attachment;
import com.neocortex.payloads.mood.CreateMoodRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJournalRequest {
    private String prompt;

    @NotNull(message = "Mood is required")
    private CreateMoodRequest mood;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private Attachment attachment;

}