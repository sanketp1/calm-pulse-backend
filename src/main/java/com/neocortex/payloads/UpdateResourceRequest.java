package com.neocortex.payloads;

import lombok.*;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateResourceRequest {
    private String title;
    private String description;
    private List<String> tags;
    private Duration duration;
    private List<String> instructions;
    private List<String> benefits;
    private List<String> attachments;
}