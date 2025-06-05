package com.neocortex.payloads.resource;

import com.neocortex.models.embeddables.Attachment;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateResourceRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private List<String> tags;

    private Duration duration;

    private List<String> instructions;

    private List<String> benefits;

    private List<Attachment> attachments;

}