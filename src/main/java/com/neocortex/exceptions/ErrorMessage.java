package com.neocortex.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@Builder
public class ErrorMessage {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    private Map<String, String> errors; // Added for validation errors
}