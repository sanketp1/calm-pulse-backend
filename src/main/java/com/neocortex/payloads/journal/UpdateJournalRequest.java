package com.neocortex.payloads.journal;

import com.neocortex.models.embeddables.Attachment;
import com.neocortex.payloads.mood.MoodPayload;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateJournalRequest {
    private String prompt;
    private MoodPayload mood;
    private String title;
    private String description;
    private Attachment attachment;
}