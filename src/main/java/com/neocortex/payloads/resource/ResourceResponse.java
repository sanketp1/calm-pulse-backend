package com.neocortex.payloads.resource;

import com.neocortex.models.embeddables.Attachment;
import lombok.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceResponse {
    private Long id;
    private String title;
    private String description;
    private List<String> tags;
    private Duration duration;
    private List<String> instructions;
    private List<String> benefits;
    private List<Attachment> attachments;
    private UUID userId;
}