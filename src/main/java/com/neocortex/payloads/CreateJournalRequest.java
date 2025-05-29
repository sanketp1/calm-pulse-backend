package com.neocortex.payloads;

import com.neocortex.models.embeddables.Attachment;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Mood is required")
    private CreateMoodRequest mood;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private Attachment attachment;

}