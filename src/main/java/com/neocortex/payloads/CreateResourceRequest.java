package com.neocortex.payloads;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

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

    private List<String> attachments;

    @NotNull(message = "User ID is required")
    private UUID userId;
}