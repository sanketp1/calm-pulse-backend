package com.neocortex.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateJournalRequest {
    private String prompt;
    private String mood;
    private String title;
    private String description;
    private String attachmentURL;
}